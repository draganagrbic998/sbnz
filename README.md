# Пројекат из предмета Системи базирани на знању

Чланови тима:
1. Драгана Грбић SW22/2017
2. Петар Николић SW31/2017

Упутство за покретање:

1. клонирати репозиторијум са git -clone https://github.com/draganagrbic998/sbnz
2. импортовати пројекте backend и knowledge у Eclipse
3. десни клик на backend пројекат у Project Explorer-у у Eclipse-у
4. \*отићи на https://www.baeldung.com/lombok-ide#eclipse и пратити упутство за Eclipse (3. Lombok in Eclipse)
5. Properties -> Java Build Path -> Libraries -> Classpath -> Add External JARs
6. Пронаћи и одабарти lombok.jar
7. Open -> Apply and Close
8. десни клик на backend пројекат у Project Explorer-у у Eclipse-у
9. Maven -> Update Project...
10. десни клик на knowledge пројекат у Project Explorer-у у Eclipse-у
11. Run As -> Maven install
12. Boot Dashborad -> local -> десни клик на backend -> (Re)start
13. u CommandPrompt-u се позиционирати у frontend folder у клонираном репозиторијуму
14. унети ng serve --open и притиснути Enter

\* уколико инсталациони програм за lombok не може да лоцира Eclipse, одабарти произвољну локацију и инсталирати бибилиотеку у њој
