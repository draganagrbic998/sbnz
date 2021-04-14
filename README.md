# Пројекат из предмета Системи базирани на знању

Чланови тима:
1. Драгана Грбић SW22/2017
2. Петар Николић SW31/2017

Линк на демонстрациони видео:
https://www.mediafire.com/file/npsaxpbvdyq600e/sbn2z.mp4/file

Упутство за покретање:
1. инсталирати Eclipse IDE for Java EE Developers
2. у Еclipsе-у instalirati „JBoss tools“:
  - Help -> Eclipse Marketplace..
  - у search укуцати JBoss Tools, након проналаска алата кликнути на install
  - чекирати све опције и инсталирати
3. са сајта www.drools.org скинути „Drools and jBPM tools“ и распаковати архиву
4. у Еclipsе-у инсталирати „Drools and jBPM tools“:
  - Help -> Install new software
  - у оквиру „Work with“ кликнути на Add… дугме
  - у новоотвореном дијалогу у поље name унети „drools local update site“
  - кликнути на дугме Local…
  - позиционирати се на org.drools.updatesite фолдер који се налази у binaries фолдеру распаковане архиве
  - чекирати све опције и инсталирати
5. након инсталације са сајта www.drools.org скинути drools distribution (Drools Engine) и распаковати архиву
6. у Еclipsе-у додати drools runtime:
  - Window -> Preferences
  - Drools -> Installed Drools Runtimes
  - кликнути на дугме ADD
  - у дијалогу кликнути на дугме Browse и пронаћи binares фолдер у оквиру drools distribution
7. клонирати репозиторијум са git clone https://github.com/draganagrbic998/sbnz
8. импортовати пројекте backend и knowledge у Eclipse
9. десни клик на backend пројекат у Project Explorer-у у Eclipse-у
10. \*отићи на https://www.baeldung.com/lombok-ide#eclipse и пратити упутство за Eclipse (3. Lombok in Eclipse)
11. Properties -> Java Build Path -> Libraries -> Classpath -> Add External JARs
12. Пронаћи и одабарти lombok.jar
13. Open -> Apply and Close
14. десни клик на backend пројекат у Project Explorer-у у Eclipse-у
15. Maven -> Update Project...
16. десни клик на knowledge пројекат у Project Explorer-у у Eclipse-у
17. Run As -> Maven install
18. Boot Dashborad -> local -> десни клик на backend -> (Re)start
19. u CommandPrompt-u се позиционирати у frontend folder у клонираном репозиторијуму
20. унети npm install и притиснути Enter
21. унети npm start и притиснути Enter
22. креденцијали за пријаву на систем:
  - admin@gmail.com, asd
  - sluzbenik@gmail.com, asd
  - klijent@gmail.com, asd

\* уколико инсталациони програм за lombok не може да лоцира Eclipse, одабарти произвољну локацију и инсталирати библиотеку у њој
