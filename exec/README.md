# OWO (오운완 : 오늘의 운동 완료)

## 오운완의 기획 의도
![기획의도](https://accidental-postage-c1a.notion.site/a0ab90782f1f492e80dada73a8f9838d)
## 프로젝트 기간 - 2022.07.11 ~ 2022.08.19

## 오운완 서비스 화면

### 경쟁모드
### 게임 - 스쿼트
![스쿼트](url 넣기)

### 게임 - 버피
![버피](url 넣기)

### 게임 - 런지
![런지](url 넣기)

### 경쟁모드 끝났을 때 - 포인트 획득 및 기록 
![경쟁 끝](url)

### 영상모드
### 영상
![영상모드](url 넣기)

### 자유모드
### 자유
![자유모드](/uploads/b8980086181c568d93a3ee73b04aa7b7/자유모드캡쳐.PNG)

### 랭킹
![랭킹]

### 마이페이지 - 경쟁모드 티어, 운동비율, 기록, 캘린더, 신체정보 분석, 정보 업데이트
![마이페이지](url)

### 로그인
![로그인]()

### 회원가입
![회원가입]()

### 회원가입 이메일 인증
![회원가입]()

### 튜토리얼
![튜토리얼]()

## 주요 기능

- 서비스 설명 : 코로나, 귀찮음, 장소의 제약에서 벗어나 집에서 친구들과 함께 경쟁하며 운동을 즐길 수 있는 서비스
- 주요 기능 :
  - web RTC + teachable machine을 사용한 실시간 화상 운동 게임
  - teachable machine을 통해 자동으로 자세를 인식하여 count
  - 기록을 통한 랭킹 시스템 구현하여 운동에 대한 재미와 동기를 부여함.

## 개발 환경

---
**BackEnd**
- InterlliJ
- spring boot ver
- spring-boot-jpa
- spring security
- Java
- AWS EC2
- mySql
- 

**Frontend**

- @babel/core@7.18.9
- @babel/eslint-parser@7.18.9
- @teachablemachine/pose@0.8.6
- @tensorflow/tfjs@3.19.0
- @vue/cli-plugin-babel@5.0.8
- @vue/cli-plugin-eslint@5.0.8
- @vue/cli-plugin-router@5.0.8
- @vue/cli-plugin-vuex@5.0.8
- @vue/cli-service@5.0.8
- @vue/eslint-config-airbnb@6.0.0
- axios@0.27.2
- bootstrap-vue-3@0.1.14
- bootstrap-vue@2.22.0
- bootstrap@5.2.0
- emoji-mart-vue-fast@11.1.1
- eslint-plugin-import@2.26.
- expose-loader@4.0.0
- html2canvas@1.4.1
- jquery@3.6.0
- multer@1.4.5-lts.1
- openvidu-browser@2.22.0
- sweetalert2@11.4.26
- v-calendar@3.0.0-alpha.8
- vue-cookies@1.8.1
- vue-router@4.0.13
- vue-sweetalert2@5.0.5
- vue@3.2.37
- vue3-carousel@0.1.40
- vue3-circle-progress@1.0.7
- vue3-youtube@0.1.9
- vuex-persistedstate@4.1.0
- vuex@4.0.2
- youtube-iframe@1.0.3

**CI/CD**
- AWS EC2
- docker
- nginx
- jenkins

### Jenkins를 이용한 CD 구축 및 SSL 인증서 적용

---

서비스 아키텍처와 같이, Jenkins의 pipeline을 이용하여 자동 배포를 구축하였음.
Gitlab webhook을 설정하여 Jenkins에 빌드 트리거를 설정했고, 이에 따라 Gitlab에서 master 브랜치에 push하면 자동으로 배포될 수 있도록 구축하여 개발하는 과정에서 배포로 인한 시간 낭비를 줄였습니다.
또한 프론트엔드인 Vue3는 Nginx와 함께 docker image로 빌드하여 배포하였고, 백엔드 및 redis, openvidu 또한 docker container로 배포하였습니다. 그리고 Nginx와 letsencrypt를 이용하여 ssl 인증서를 적용하였고, 프론트엔드는 443(https)로 프록시로 분기시켰고 백엔드는 /api 경로로 프록시를 걸어줬습니다.

### 기술 특이점

---

- Teachable Machine

자세를 학습시켜 pose detection을 통한 모션 인식으로 자세 인식을 통해 유저가 각 운동 종목을 잘 수행하는지 체크했고, 운동 개수를 카운팅할 수 있도록 하였습니다. 

이런 식으로 Teachable Machine으로 학습을 시키면 자세별로 도출된 값을 이용할 수 있게 됩니다. 여기서 추출된 코드와 값으로 오운완에 맞게 가공하여 구현하였습니다.

- Redis

(수정필요할듯? 무엇인지 잘 모르겠음...!)

랭킹 기능에 들어가는 랭킹 정보는 자정마다 업데이트 되는 정보여서 단순한 구조의 정보이고, 반복적으로 동일하게 제공되고, 최신화가 실시간으로 필요하지 않은 정보였습니다. 이러한 데이터의 특성으로 캐싱을 적용하기에 적절하다고 생각을 했고, Redis에 랭킹 정보를 저장하여 DB를 거치지 않고 정보를 가져와 트래픽이 많아질 때 백엔드 부하를 줄이고, 정보 조회 속도를 높였습니다. 또한 저희는 Spring Security와 JWT를 이용하여 인증을 구현하였는데, Redis를 이용해 로그아웃시킨 토큰들을 만료처리하여 해당 토큰으로는 다시 인증할 수 없도록 구현하였습니다.


- webRTC(openVidu)
openVidu로만 할 수 있는 기능 뿐만 아니라 백엔들르 함께 이용한 개발로 여러 기능을 구현하였습ㄴ다.
각 방마다 6명의 인원이 들어갈 수 있게 구현하였고, 방장만 게임을 시작 할 수 있게 하였고, secret 방을 만들 수 있게 하여 비밀번호를 아는 사용자 외에는 들어오지 못하게 구현 하였습니다. 

- vuex 
프론트엔드 구현 시에 vue3와 vuex를 이용하여 상태관리를 하였습니다.

- 배포
docker, Nginx, jenkins를 이용하여 자동 무중단 배포를 구현 하였습니다.
백엔드를 docker 컨테이너로 배포하였고, 프론트는 Nginx와 함께 docker 컨테이너로 배포하였습니다.

## 협업툴

- Figma
- Git
- Jira
- Notion
- MatterMost
- gatherTown
- webex

### 화면설계(Figma)
![Figma](https://www.figma.com/file/g2NqVsk5UqVBHffBCQx57k/%23%EC%98%A4%EC%9A%B4%EC%99%84)

### git 컨벤션

```bash
git commit -m '[F/B /ETC] 타입_제목  ### 제목은 최대 50 글자까지만 입력 ###

#이슈번호
본문내용입니다. 한 줄에 72글자까지 작성 제한'
### 본문은 제목에서 한 줄 띄고, 한 줄에 72자까지만 입력 ###

# <타입> 리스트
#   ADD : 추가(새로운 기능)
#   UPDATE : 수정(기능 수정중임)
#   FIX     : 버그 (버그 수정)
#   REFACTOR: 리팩토링
#   STYLE   : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
#   DOCS    : 문서 (문서 추가, 수정, 삭제)
#   TEST    : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
#   CH   : 기타 변경사항 (빌드 스크립트 수정 등)
#   COMPLETE : 완료
# ------------------
#     타입은 대문자로
#     제목 끝에 마침표(.) 금지
#     제목과 본문을 한 줄 띄워 분리하기
#     본문은 "어떻게" 보다 "무엇을", "왜"를 설명한다.
# ------------------

#예시
[F] : ADD_main.html 메인페이지 작성함.
[B] : ADD_main.html
[B] : ADD_dao, dto, service 수정 
[ETC] : DOCS_20220510.md 5월 10일 회의록 작성
```

### git branch 전략
- Git Flow Model을 사용

- Git Flow 사용 브랜치 
  - feature - 기능
  - develop - 개발
    - front_dev - FrontEnd 개발
    - back_dev - BackEnd 개발
  - main - 배포

- Git Flow 진행 방식
  1. feature 브랜치에서 완성되면, 각 front_dev, back_dev 브랜치로 pull request를 통해 merge한다.
    ⇒ pull request가 요청되면, 팀원들이 코드 리뷰를 하여 안전하게 merge한다.
  2. 매 주마다 front_dev, back_dev 브랜치를 develop 브랜치로 병합하고, main으로 병합하여 배포를 진행한다.

### Jira
---
협업 및 일정, 업무 관리를 위해 Jira를 사용하였습니다.
스프린트 기간동안 진행되어야 할 주 단위 계획을 짜고, 진행할 이슈들을 스프린트로 만들어 등록했습니다.
스프린트는 일주일 단위로 진행하였습니다.
- Epic : back, front로 나누어 구성하였습니다.
- story : '로그인 버튼을 누르면 로그인이 되고, 메인페이지로 이동한다' 와 같이 자세하게 작성하였습니다.
- subtask : story를 세세하게 나누어 업무를 관리 하였습니다.

### Notion
---
API 명세, git 전략, project 관련 페이지 등을 모아 관리하였습니다. 그리고 항상 모든 회의 및 피드백은 기록으로 남겨두어서 잘 반영될 수 있도록 진행하였습니다.
특히, 네이밍 규칙, 브랜치 전략 등도 노션에 기록하여 모두가 항시 확인하여 잘 지킬 수 있도록 하였습니다.

### Scrum
---
매일 아침 9시에 어제 했던 일, 오늘 할 일, 이슈를 각자 정리해서 5~10분 동안 서로 공유하는 시간을 가졌습니다. Scrum을 통해 팀원들의 현재 상황을 공유하여 프로젝트에 대해서 꾸준히 모니터링을 할 수 있었습니다. 매일 진행했던 Scrum 회의록은 Notion에 정리했습니다.
