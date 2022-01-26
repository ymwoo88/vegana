# 프로젝트 내용 설명

---
## 1. Model 구성

Model 구성은 검색, 요청&응답, 엔티티 3가지로 구분함 

### 검색용  
리스트 페이지의 검색필드 또는 페이징처리에 필요한 요청 정보를 담는다.
```
@Getter
@Setter
public class MemberSearch extends AbstractSearch {

    private String nickname;

    private String email;
}
```
공통으로 사용되는 항목은 AbstractSearch 객체에 모아두었다  
그리고 단순히 모아둔 것이아닌 다른 용도로 쓰이기도 한다. (자세한 건 나중에 설명)

AbstractSearch 내용을보면 공통으로 들어가는 페이지 정보를 선언하였고  
Pageable, Sort 정보를 얻기위한 로직을 생성함
```
@Getter
@Setter
public abstract class AbstractSearch
{
    private Pageable     pageable;
    private Integer      page;
    private Integer      size;
    private List<String> sort;

    @JsonIgnore
    public Pageable getPageable() {
        ....
    }

    public Sort extractSort() {
        ....
    }
}
```


### 요청&응답  
http 통신시 주고 받는 Dto용 객체이다.  
데이터를 저장, 수정, 삭제, 단건조회 할 때 요청객체로도 사용 할 수 있으며  
서버에서 응답 시 객체로 사용된다.
```
@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class MemberDto extends AbstractDto<Long, Member, MemberDto> {

    private Long id;

    private String email;

    private String password;

    private Member.Authority authority;

    private String name;

    private String nickname;

    private String profileUrl;

    private String phoneNo;

    private String dateOfBirth;

    public MemberDto of(final Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .authority(member.getAuthority())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .phoneNo(member.getPhoneNo())
                .dateOfBirth(member.getDateOfBirth())
                .build();

    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .phoneNo(phoneNo)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
```
우선.. 이것저것 궁금한것들이 있을 수 있다 하나하나씩 알아보자

1. 생성자관련 어노테이션은 알겠는데 access = AccessLevel.PROTECTED 는 뭐지?
```
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
```
그래서 직접 확인해보았다.  
큰 사용 용도가 있는 것은 아니고 무분별한 생성자 사용을 제한할 수 있다.   
가끔 소스코드를 보면 new 를 난무하여 사용하는 케이스가 있는데 가독성도 떨어지고 코딩할 때 귀찮아진다.  
그래서 빌더패턴을 유도하는 이유도 있다.
```
// 무분별한 생성자를 사용하는 것을 금지할 수 있다. (아래 모두 오류라인이뜨고있다.)  
MemberDto memberDto1 = new MemberDto(1, "ss", "", Member.Authority.ROLE_USER);  
MemberDto memberDto2 = new MemberDto(1, "ss", "", Member.Authority.ROLE_USER, "", "", "", "", "");

// 그리고 Builder로만 생성을 유도하기도 한다.
MemberDto memberDto = MemberDto.builder()
        .id(1L)
        .email("")
        .build();
```  

2. AbstractDto<Long, Member, MemberDto> 상속객체는 뭐지?  

자세한 내막은 (AbstractService) 주제를 보면 이해가 쉽다.  
우선 ID, Entity, Dto 타입을 주입받아서 공통으로 수행할 수 있는 메소드들을 구현해두었다.  
Dto to Entity 와 Entity to Dto 서로 객체 치환 목적을띠는 메소드 2개가 있으며  
isNew 메소드는 Persistable<ID> 구현체 메소드이고 Entity ID 값을 체크하는 용도이다.
```
public abstract class AbstractDto<ID, Entity, Dto> implements Persistable<ID> {

    public Dto of(final Entity entity) {
        return null;
    }

    public Entity toEntity() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isNew()
    {
        return getId() == null;
    }
}
```

### 엔티티
DB 관련 모델이다.
```
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Table(name = "MEMBER")
@Entity
public class Member extends AbstractAudit<Member> {

    public enum Authority {
        ROLE_USER, ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_KEY")
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTHORITY")
    private Authority authority;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    @Builder
    protected Member(final String email,
                     final String password,
                     final Authority authority,
                     final String name,
                     final String nickname,
                     final String profileUrl,
                     final String phoneNo,
                     final String dateOfBirth) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.name = name;
        this.phoneNo = phoneNo;
        this.dateOfBirth = dateOfBirth;
    }

    public void update(final Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.authority = member.getAuthority();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.profileUrl = member.getProfileUrl();
        this.phoneNo = member.getPhoneNo();
        this.dateOfBirth = member.getDateOfBirth();
    }
}
```

여기서 또 궁금한게 있을 것이다. 하나하나씩 확인해보자

1. @EqualsAndHashCode(of = {"id"}, callSuper = false) 는 뭐자?

    equals와 hashcode를 만들어 주는 것  
    equals: 두 객체의 내용이 같은 지 확인  
    hashcode: 두 객체가 같은 객체인지 확인  
    Tip.  
    @EqualsAndHashCode(of="id"): 연관 관계가 복잡해 질 때,  
    @EqualsAndHashCode에서 서로 다른 연관 관계를 순환 참조하느라 무한 루프가 발생하고,   
    결국 stack overflow가 발생할 수 있기 때문에 id 값만 주로 사용
    
    callSuper 속성을 통해 eqauls와 hashCode 메소드 자동 생성 시 부모 클래스의 필드까지 감안할지의 여부를 설정할 수 있다.  
    @EqualsAndHashCode(callSuper = true)로 설정시 부모 클래스 필드 값들도 동일한지 체크하며,  
    false(기본값)일 경우 자신 클래스의 필드 값만 고려한다.


2. AbstractAudit<Member> 상속 객체는 뭐지?

    엔티티의 공통 기능이 존재 한다.  
    날짜의 경우 자동으로 생성되고, 생성자/수정자의 경우 스프링시큐리티를 연동하면 자동으로 주입 된다.
    ```
    @Getter
    @Setter
    @MappedSuperclass
    @EntityListeners(AuditingEntityListener.class)
    public abstract class AbstractAudit<Entity> implements Serializable {
    
        /** 생성시간 */
        @CreatedDate
        @Column(name = "CREATED_AT", nullable = false)
        protected LocalDateTime createdAt;
    
        /** 수정시간 */
        @LastModifiedDate
        @Column(name = "UPDATED_AT", nullable = false)
        protected LocalDateTime updatedAt;
    
        /** 생성자 */
        @CreatedBy
        @Column(name = "CREATED_NAME")
        protected String createdName = "ANONYMOUS";
    
        /** 수정자 */
        @LastModifiedBy
        @Column(name = "UPDATED_NAME")
        protected String updatedName = "ANONYMOUS";
    
        public void update(final Entity entity) {
        }
    }
    ```
3. enum이 왜 entity 안에?

    Entity의 특별하게만 사용되는 enum의 경우 가독성이 더 좋기에 사용한다.  
    공통성향의 enum 별도의 패키지 경로에 구현하기도 한다.

--- 
