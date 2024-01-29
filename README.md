# Front

FE 규칙 
1. Activity/Fragment 는 종목별로 패키지별로 저장 (단, product 클래스의 dataclass일 경우 product_data와 같이 따로 패키지 생성)

2. vector파일 불러올때 앞에 다음과 같이 명칭
(개발클래스명_버튼명    ex) product_bottombtn.xml)

3. Push 할때 사용할 명령어 아래와 같이 정리
- git branch MH
- git commit -m "메세지"
- git push -f origin MH

4. Push 이후 Merge과정 참고사항
- git checkout origin/(브랜치명) : 생성된 브랜치로 전환하기
- git fetch —all : 업데이트된 브랜치 확인하기 (무조건 필요함!!)
- git merge (merge할 브랜치명) : 전환된 브랜치에서 merge
- conflict 나면 수정
