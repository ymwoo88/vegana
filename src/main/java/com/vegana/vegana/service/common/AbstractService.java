package com.vegana.vegana.service.common;

import com.vegana.vegana.exception.VgnRuntimeException;
import com.vegana.vegana.exception.message.CommonErrorCode;
import com.vegana.vegana.exception.message.DBErrorCode;
import com.vegana.vegana.model.common.AbstractAudit;
import com.vegana.vegana.model.common.AbstractDto;
import com.vegana.vegana.model.common.AbstractSearch;
import com.vegana.vegana.repository.common.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class AbstractService<
            ID,
            Entity extends AbstractAudit<Entity>,
            Dto extends AbstractDto<ID, Entity, Dto>,
            Search extends AbstractSearch,
            BaseRepository extends JpaRepository<Entity, ID> & SearchRepository<Dto, Search>
        >
        implements CrudService<ID, Dto, Search> {

    @Autowired
    protected BaseRepository baseRepository;

    public Dto create(Dto dto) {

        return Optional.of(
                    Optional.ofNullable(this.createPreProcess(dto)) // 전처리
                            .orElse(dto)
                )
                .map(Dto::toEntity)
                .map(baseRepository::save)
                .map(dto::of)
                .map(resource ->
                        Optional.ofNullable(this.createPostProcess(resource)) // 후처리
                                .orElse(resource)
                )
                .orElseThrow(() -> new VgnRuntimeException(DBErrorCode.INSERT_ERROR));
    }

    @Override
    public Dto detail(Dto dto) {

        return Optional.ofNullable(
                    Optional.ofNullable(this.detailPreProcess(dto)) // 전처리
                            .orElse(dto.getId())
                )
                .map(baseRepository::findById)
                .flatMap(entity -> entity)
                .map(dto::of)
                .map(resource ->
                        Optional.ofNullable(this.detailPostProcess(resource)) // 후처리
                                .orElse(resource)
                )
                .orElseThrow(() -> new VgnRuntimeException(DBErrorCode.SELECT_ERROR));
    }

    @Override
    public List<Dto> searchList(Search search) {

        return Optional.of(
                    Optional.ofNullable(this.searchListPreProcess(search)) // 전처리
                            .orElse(search)
                )
                .map(baseRepository::searchList)
                .map(resource ->
                        Optional.ofNullable(this.searchPostListProcess(resource)) // 후처리
                                .orElse(resource)
                )
                .orElseThrow(() -> new VgnRuntimeException(DBErrorCode.SELECT_LIST_ERROR));
    }

    @Override
    public Page<Dto> searchPage(Search search) {

        return Optional.of(
                    Optional.ofNullable(this.searchPagePreProcess(search)) // 전처리
                            .orElse(search)
                )
                .map(baseRepository::searchPage)
                .map(resource ->
                        Optional.ofNullable(this.searchPagePostProcess(resource)) // 후처리
                                .orElse(resource)
                )
                .orElseThrow(() -> new VgnRuntimeException(DBErrorCode.SELECT_PAGE_ERROR));
    }

    @Override
    public Dto update(Dto dto) {

        return Optional.ofNullable(dto.getId())
                .flatMap(baseRepository::findById)
                .map(findEntity -> {
                    Entity newEntity = dto.toEntity();
                    findEntity.update(newEntity); // 변경 할 항목 복사
                    findEntity.setUpdatedAt(LocalDateTime.now()); // 변경 날짜 현재 시간 SET
                    return Optional.ofNullable(this.updatePreProcess(findEntity, newEntity)) // 전처리
                            .orElse(findEntity);
                })
                .map(baseRepository::save)
                .map(dto::of)
                .map(resource ->
                        Optional.ofNullable(this.updatePostProcess(resource)) // 후처리
                                .orElse(resource)
                )
                .orElseThrow(() -> new VgnRuntimeException(DBErrorCode.UPDATE_ERROR));
    }

    @Override
    public void delete(ID id) {

        if (Objects.isNull(id)) {
            throw new VgnRuntimeException(CommonErrorCode.INVALID_REQUEST_PARAM);
        }

        try {
            this.deletePreProcess(id); // 전처리
            baseRepository.deleteById(id);
            this.deletePostProcess(id); // 후처리
        } catch (Exception e) {
            throw new VgnRuntimeException(DBErrorCode.DELETE_ERROR);
        }
    }

    /**
     * This is Method Optional.
     * 상위 Override 된 메소드들의 전후 처리를 위한 메소드 (선택사항)
     * 전처리(preProcess)
     * 후처리(postProcess)
     *
     * 사용 예)
     * - CRUD 전후 값 변경이 필요 할 때
     * - 트리거 형태로 다른 로직 처리가 필요할 때(API, ES, REDIS..)
     */
    protected Dto createPreProcess(Dto dto) {return null;} // 등록 전처리
    protected Dto createPostProcess(Dto dto) {return null;} // 등록 후처리

    protected ID detailPreProcess(Dto dto) {return null;} // 상세조회 전처리
    protected Dto detailPostProcess(Dto dto) {return null;} // 상세조회 후처리

    protected Search searchListPreProcess(Search search) {return null;} // 리스트 조회 전처리
    protected List<Dto> searchPostListProcess(List<Dto> dto) {return null;} // 리스트 조회 후처리
    protected Search searchPagePreProcess(Search search) {return null;} // 페이지 조회 전처리
    protected Page<Dto> searchPagePostProcess(Page<Dto> dto) {return null;} // 페이지 조회 후처리

    protected Entity updatePreProcess(Entity before, Entity after) {return null;} // 업데이트 전처리
    protected Dto updatePostProcess(Dto dto) {return null;} // 업데이트 후처리

    protected void deletePreProcess(ID id) {} // 삭제 전처리
    protected void deletePostProcess(ID id) {} // 삭제 후처리
}
