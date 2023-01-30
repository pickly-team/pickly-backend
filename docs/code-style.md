# Code Style

## Checkstyle Set Up

- Google Checks

#### how to set up (IntelliJ)
1. Preferences > Plugins 에서 checkstyle 검색 및 설치
2. 설치 후 IntelliJ 리로드
3. System > Preferences > Tools > Checkstyle
4. Google Checks 선탣s

#### how to use
1. 원하는 파일에 돌리기
2. commit 시에 checkstyle 검사하기

## Formatter Set Up

- Google Style 
  - [Google Java Formatter Guide](https://google.github.io/styleguide/javaguide.html)
  - [intellij-java-google-style.xml](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml)

#### how to set up (IntelliJ)
1. intellij-java-google-style.xml 다운로드
2. File > Settings > Code Style > Schema (톱니바퀴 선택) > Import Scheme > Intellij IDEA code style XML
3. intellij-java-google-style.xml 선택 
4. Apply 버튼 클릭