# Conventions

## Branch

#### Branches
- main
- feat/{issue-number}/*
  - 기능 개발 브랜치

#### Git Branch Protection
- approve 하나 이상 
- CI/CD가 붙은 경우, 빌드 통과
- 코드 충돌 없음
- 변경 요청하거나 제안사항 있으면 수정사항 반영 후 다시 리뷰 요청
- 조건 만족시 self-merge

<br />

## Commit
- [Angular Commit Convention](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)

<br />

#### Structure
```
커밋 메시지 헤더
<type> : <short summary>
  │         │
  │         └─⫸ 명령문, 현재 시제로 작성합니다. 대문자를 사용하지 않으며, 마침표로 끝내지 않습니다.
  │       
  │
  └─⫸ Commit Type: build|ci|docs|feat|fix|perf|refactor|test
The <type> and <summary> fields are mandatory, the (<scope>) field is optional.

body
  │
  └─⫸ 한글로 작성. 옵션
```

#### Type
- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 관련
- style : 스타일 변경 (포매팅 수정, 들여쓰기 추가, …)
- refactor : 코드 리팩토링
- test : 테스트 관련 코드
- build : 빌드 관련 파일 수정
- ci : CI 설정 파일 수정
- perf : 성능 개선
- chore : 그 외 자잘한 수정


<br />

Reference
- https://github.com/angular/angular/blob/main/CONTRIBUTING.md#commit-message-footer
- https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/defining-the-mergeability-of-pull-requests