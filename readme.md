В данном руководстве описаны:

* [системные требования](#req)
* [подготовка](#preparing) Eclipse Standart (впрочем, оно также подходит для версии Eclipse for Java developers, ибо тех Maven- и WindowBuilder-плагинов, что там стоят, недостаточно) к импорту данного Maven-проекта
* собственно [импорт](#import) и сборка данного проекта
* сборка для разных [платформ](#crossbuilding)
* возникшие в процессе и решённые [проблемы](#troubles)
* [ссылки](#links) различной степени полезности

a.maven-цели
============

Для сботки используется build manager [Maven](http://maven.apache.org/);
некоторые плагины были отключены(напр.: maven-jar-plugin) и заменены, т. к. в нашем usecase они делали совсем не то, что нужно...
а некоторые цели(goals) были переопределены:

- **clean** -- очистка
- **compile** -- компиляция
- **package** :
    - сборка **.jar** -- для любой платформы, возможен кроссбилдинг, см. [в соответствующей главе](#crossbuilding)  
    - сборка  **.exe** / **исполняемого файла** -- только для своей платформы, кроссбилдинга нет
- **deploy** -- отправка `*.jar`, `*win32*.exe`, `*win64*.exe` на FTP

b.Системные требования<a name="req"></a>
=========================================

> Требования? А где ожидаемая кроссплатформенность, мы же используем Java...

1. Требования к IDE<a name="idereq"></a>
----------------------------------------

Возникли из-за проблем совместимости версий Eclipse с плагинами для него(`m2e`, `Maven SCM Handler for EGit`).  
Так вот, руководство актуально для `Eclipse Kepler`: **Eclipse Standard 4.3** или **Eclipse IDE for Java Developers 4.3** на момент 2 ноября 2013.

У товарища была [проблема](#eejunotrouble) при использовании `Eclipse IDE for Java EE Developers` версии `Juno`.

2. Требования к версиям библиотек
---------------------------------

Описаны в файле `pom.xml` :)

Учитывая отсутствие `.project`, `.settings`, а тем более `.classpath` проект *теоретически* можно собрать даже без среды.

3. Требования к ОС
------------------

Являются следствием системозависимости либы `SWT`, на данный момент(6 ноября 2013) в `pom.xml` созданы профили для `Windows x86`, `Windows AMD64`, `Linux x86` и `Linux AMD64`.

`Mac OS` не поддерживается библиотекой `Hunspell-BridJ`.

c.Подготовка Eclipse<a name="preparing"></a>
============================================

I. Добавление кнопок git на панель
----------------------------------

[Взято из предыдущего моего мануала Eclipse+GitHub](http://yadi.sk/d/Bd1JqGraBCPgK)

1. ![](http://img-fotki.yandex.ru/get/9061/165433899.0/0_e6a34_b9311bf6_orig "Window -> Customize Perspective...")
2. ![](http://img-fotki.yandex.ru/get/9065/165433899.0/0_e6a35_cc927160_orig "Command Groups Availability -> Git")

II. Установка Maven-плагина
---------------------------

Взято [отсюда](http://www.apache-maven.ru/quick_start.html).  
Для установки:

1. зайдите в меню Help->Install New Software
2. Выберите Work with --All Available Sites--
3. в фильтре наберите `m2e` и отметьте плагины как показано на рисунке
![](http://img-fotki.yandex.ru/get/9480/165433899.0/0_e6a29_a835dd9_orig)
4. Отключите галочки, как написано [здесь](#diskspace), чтобы Maven не забивал Workspace лишним мусором на полгигабайта.

III. Установка коннектора Maven SCM Handler for EGit
----------------------------------------------------

Взято [отсюда](http://www.machineversus.me/2012/08/if-youve-upgraded-to-eclipse-juno.html) и обновлено.  
Коннектор нужен для того чтобы [на V-м шаге в поле 'SCM URL' был пункт 'git'](#connector), иными словами, для возможности нормального импорта Maven-проектов из Git-репозиториев.  
Мы вынуждены добавлять неподписанный плагин-коннектор из стороннего репозитория, из-за того что коннектор из официального репозитория не совместим с последними версиями Eclipse (судя по [ссылке](http://www.machineversus.me/2012/08/if-youve-upgraded-to-eclipse-juno.html), эта проблема уже была начиная с Eclipse Juno).

1. Click Help
2. Install New Software
3. Uncheck the box labeled `Group items by category` (this step is important or you won't see the connector in the table)
4. Paste in this URL <http://repository.tesla.io:8081/nexus/content/sites/m2e.extras/m2eclipse-egit/0.14.0/N/0.14.0.201305250025/> <- **осторожно, ссылка может быть обрезана из-за того что не умещается по длине** - ссылка обновлена по сравнению с той что в статье...
![Скрин](http://img-fotki.yandex.ru/get/9166/165433899.0/0_e6a24_bc33a4af_orig "Снять галочку Group items by category")
5. Eclipse предупредит об установке неподписанного плагина, соглашаемся...
6. Finish the plugin install wizard and restart the workspace

IV. Установка WindowBuilder(опционально)
------------------------------------------

* Плагин [WindowBuilder](http://www.eclipse.org/windowbuilder/ "Страница WindowBuilder на сайте Eclipse") добавляет WYSIWYG-редактор, с помощью которого можно быстро создавать окошки.  
* [Страница загрузки WindowBuilder](http://www.eclipse.org/windowbuilder/download.php), на которой нужно выбрать соответствующую вашей версии Eclipse ссылку:  
![](http://img-fotki.yandex.ru/get/5008/165433899.0/0_f1de8_1c237a11_orig)  
[Эта ссылка, например для Eclipse Kepler](http://download.eclipse.org/windowbuilder/WB/release/R201309271200/4.3/), как и все остальные, содержит инструкции по установке.
* Использование: ![](http://img-fotki.yandex.ru/get/4912/165433899.0/0_e6a3c_e7a0a5f5_orig)
![](http://img-fotki.yandex.ru/get/9092/165433899.0/0_e6a3f_b3413d1f_orig)


d.Импорт<a name="import"></a>
============================

V. Импорт maven-проекта из GitHub в Eclipse Workspace
-----------------------------------------------------

Взято [отсюда](http://stackoverflow.com/questions/4869815/importing-a-maven-project-into-eclipse-from-git) и дополнено.

1. Select the "Import..." context menu from the Package Explorer view
2. Select "Check out Maven projects from SCM" option under the Maven category
3. Выбираем SCM URL: git и вставляем ссылку:<a name="connector"></a> ![](http://img-fotki.yandex.ru/get/9106/165433899.0/0_e6a30_bdfdd25a_orig)

Здесь есть одна особенность - а именно: Eclipse может 'задуматься' на несколько секунд(Windows) или на несколько минут(Linux) перед тем, как собственно отобразить проект в Package Explorer.  
4. <a name="mavenupd"></a>Дальше обновляем проект(иногда сам Maven просит это сделать через Problems View):  
`Maven` -> `Update Project...`  
![](http://img-fotki.yandex.ru/get/4912/165433899.0/0_e6a27_2152292e_orig "Выбираем Maven -> Update Project...")  
Снимаем галочку Offline:  
![](http://img-fotki.yandex.ru/get/9109/165433899.0/0_e6a22_75102b8c_orig "Снимаем галочку Offline")  
5. Скорее всего на значке проекта будет восклицательный знак, либо красный крест - это от того что Eclipse не видит нужных библиотек(зависимостей).
![](http://img-fotki.yandex.ru/get/9168/165433899.0/0_e6b0e_b7b154b9_orig)  
Сейчас мы их скачаем:  
Запускаем Тесты: `Run As` -> `Maven test`  
![Запускаем тесты](http://img-fotki.yandex.ru/get/9163/165433899.0/0_e6a28_6ccd25e_orig "Запускаем тесты: Run As -> Maven test")  
Ждём, пока maven скачает все нужные для сборки maven-плагины и зависимости(библиотеки)... [Куда?](#repo)  
![](http://img-fotki.yandex.ru/get/6727/165433899.0/0_e6b0f_39f3aa1e_orig)  
Успех:  
![](http://img-fotki.yandex.ru/get/9584/165433899.0/0_e6b10_f93495bb_orig)  
Если на значке проекта по-прежнему остаётся восклицательный знак или красный крест, то [обновляем](#mavenupd) проект.

e.Сборка для разных платформ<a name="crossbuilding"></a>
========================================================

Предположим, у вас платформа `win x86` и вы собираетесь сделать билд для `win x86_64`.  
При попытке сборки с указанием профиля `mvn package -P winprofile64` в результирующий jar добавятся библиотеки SWT для win x86 (т. к. профиль winprofile32 активировался сам из-за тега `<activation>`), а плагин manen-assembly-plugin не произведёт замену, из-за того что содержащиеся в архиве org.eclipse.swt.win32.win32.**x86_64**-4.3.jar файлы `swt-xulrunner-win32-4332.dll, ...` имеют идентичные названия с уже добавленными файлами для org.eclipse.swt.win32.win32.**x86**-4.3.jar.  

В результате на выходе в папке `target` имеем файл Coursework-on-Software-Engineering-win64--2014-01-12--15-39-standalone.jar, который вроде бы и предназначен для x64, а на самом деле содержит x32-библиотеки, и на x64 не запустится в принципе.

Решение:  
Нужно [принудительно деактивировать](http://maven.apache.org/guides/introduction/introduction-to-profiles.html#Deactivating_a_profile) профиль вашей платформы, в нашем случае это `winprofile32` :  
`mvn package -P winprofile64,!winprofile32`  
![](http://img-fotki.yandex.ru/get/9584/165433899.0/0_e8b37_ed3bdd95_orig)


f.Известные проблемы<a name="troubles"></a>
===========================================

1. Ругается на наличие BOM: `illegal character: \65279`
------------------------------------------------------

Проблема проявилась на Linux Mint amd64, но не проявилась на Windows x86.  
[Решение:](http://stackoverflow.com/questions/1068650/using-awk-to-remove-the-byte-order-mark)  
`# Removing BOM from all text files in current directory:`  
`sed -i '1 s/^\xef\xbb\xbf//' *.java`

2. Куда подевался гигабайт дискового пространства?
--------------------------------------------------

1. <a name="repo"></a>Maven скачивает зависимости(библиотеки) и прочие maven-плагины в `~/.m2/repository` на Linux и в `%USERPROFILE%\.m2\repository` на Windows. Очищать эти папки во время работы над проектом нет смысла. 
2. <a name="diskspace"></a>По непонятным причинам Maven забирает примерно 500МБ в `Your_Workspace_path/.metadata/.plugins/org.eclipse.m2e.core/nexus`  
Это безобразие можно отключить, открыв `Window` -> `Preferences`  
![](http://img-fotki.yandex.ru/get/9311/165433899.0/0_e6b0c_d4b4aafb_orig)  
и выставив галочки вот так: ![alt-текст](http://img-fotki.yandex.ru/get/9512/165433899.0/0_e6a23_57a3866c_orig "Галочка Offline должна быть снята!")  
(решение взято [отсюда](http://stackoverflow.com/questions/8539841/eclipse-metadata-plugins-disk-space), но нужно оставить снятой галочку `Offline`, иначе не скачаются новые файлы(плагины и зависимости), а значит мы не сможем собрать только что импортированный проект).  
**Затем** можно **удалить** папку `Your_Workspace_path/.metadata/.plugins/org.eclipse.m2e.core/nexus`.

3. pom.xml скорее всего не совместим со старым maven 2.x
--------------------------------------------------------

В Eclipse, судя по возникшим проблемам, используется maven 3.x.

4. An error occurred while collecting items to be installed...
--------------------------------------------------------------

Или "Восстанавливаем работоспособность `Help -> Install New Software` и `Help -> Check for Updates` в Eclipse Kepler".  
Взято [отсюда](http://stackoverflow.com/questions/511367/error-when-updating-eclipse) и обновлено для Eclipse Kepler.

1. export the update site listing to bookmarks.xml file (Install/Update->Available Software Sites->Export)
2. stop eclipse
3. remove configuration/.settings/org.eclipse.equinox.p2.*.prefs files
`C:\Program Files\eclipse\p2\org.eclipse.equinox.p2.engine\.settings`  
`C:\Program Files\eclipse\p2\org.eclipse.equinox.p2.engine\profileRegistry\epp.package.standard.profile\.data\.settings`  
4. start eclipse
5. import the bookmarks.xml file (Install/Update->Available Software Sites->Import) that was exported in step 1

5. <a name="eejunotrouble"></a>Checking out maven projects has encountered a problem
------------------------------------------------------------------------------------

Возникает при импорте проекта(`Import...` -> `Check out Maven projects from SCM` -> `SCM URL`) на старых версиях Eclipse.  
![](http://img-fotki.yandex.ru/get/9161/165433899.0/0_e6b0b_153e0b3f_orig)  
Решение: [Используйте Eclipse Kepler 4.3](#idereq)

6. No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
---------------------------------------------------------------------------------------------------

`[INFO] —-----------------------------------------------------------`  
`[ERROR] COMPILATION ERROR :`  
`[INFO] —-----------------------------------------------------------`  
`[ERROR] No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?`

Проблема возникает из-за того что встроенный Maven не видит JDK, и как следствие не может скомпилировать:

* Если не установлена JDK - установить.
* JDK установлена, но в Eclipse сбились `Installed JREs` - выставлена JRE:  
![](http://img-fotki.yandex.ru/get/9107/165433899.0/0_e6bc4_c24be19c_orig)  
Должна быть JDK:  
![](http://img-fotki.yandex.ru/get/9319/165433899.0/0_e6bc3_bf80314f_orig)

7. Неправильно отображаются русские буквы
-----------------------------------------

![](http://img-fotki.yandex.ru/get/9512/165433899.0/0_e74ac_5d994ca0_orig)  
Решение: `Properties` -> `Resource` -> `Text File Encoding` -> `UTF-8`  
![](http://img-fotki.yandex.ru/get/9068/165433899.0/0_e74aa_d1dfb8ce_orig)  
![](http://img-fotki.yandex.ru/get/4906/165433899.0/0_e74ab_12aac62_orig)  
Результат:  
![](http://img-fotki.yandex.ru/get/9326/165433899.0/0_e74a9_bebeee36_orig)  

8. Внезапно появились ошибки - Eclipse не видит импортируемые пакеты
--------------------------------------------------------------------

![](http://img-fotki.yandex.ru/get/9168/165433899.0/0_e6b0e_b7b154b9_orig)  
Решение: [обновите](#mavenupd) проект.  

9. Не работает maven-release-plugin :
-------------------------------------

`[ERROR] Failed to execute goal org.apache.maven.plugins:maven-release-plugin:2.0:branch (default-cli) on project Coursework-on-Software-Engineering: Unable to commit files`  
`[ERROR] Provider message:`  
`[ERROR] The git-add command failed.`  
`[ERROR] Command output:`  
`[ERROR] "git" не является внутренней или внешней`  
`[ERROR] командой, исполняемой программой или пакетным файлом.`  

Решение:  
1. Добавьте путь к git.exe в PATH  
![Добавьте путь к git.exe в PATH](http://img-fotki.yandex.ru/get/9814/165433899.0/0_f1df1_c62aac40_orig)  
1.1 Удостоверьтесь, что git.exe виден из консоли, набрав git:  
![](http://img-fotki.yandex.ru/get/9834/165433899.0/0_f38e2_bdaa78b1_orig)  
2. **Перезапустите** Eclipse  


10. git, запущенный maven-release-plugin'ом требует имя и email:
----------------------------------------------------------------

`[ERROR]`  
`[ERROR] *** Please tell me who you are.`  
`[ERROR]`   
`[ERROR] Run`  
`[ERROR]`   
`[ERROR] git config --global user.email "you@example.com"`  
`[ERROR] git config --global user.name "Your Name"`  
`[ERROR]`   
`[ERROR] to set your account's default identity.`  
`[ERROR] Omit --global to set the identity only in this repository.`  
`[ERROR]`  

Решение:  
1. ![Window -> Preferences -> Team -> Git -> Configuration -> System Settings -> Add Entry...](http://img-fotki.yandex.ru/get/9830/165433899.0/0_f1df2_90bbda76_orig)  
2. ![Key: user.name, Value: your_name](http://img-fotki.yandex.ru/get/9061/165433899.0/0_f1df4_8444f0c5_orig)  
3. ![Key: user.email, Value: your_email@gmail.com](http://img-fotki.yandex.ru/get/9747/165433899.0/0_f1df3_35372bf0_orig)  


11. maven-release-plugin зависает на git push или куда вписывать логин-пароль?<a name="release_properties"></a>
------------------------------------------------------------------------------

В консоли отображается  
`[INFO] Executing: cmd.exe /X /C "git push https://github.com/nikit-cpp/Coursework-on-Software-Engineering.git 0.1.x"`  
`[INFO] Working directory: E:\Programming\Examples 9 java\swt\Coursework-on-Software-Engineering`  
а в диспетчере задач висит процесс git.exe

Решение: нужно указать [имя и пароль](http://stackoverflow.com/questions/17973089/maven-release-plugin-with-git-repository-ignores-scm-user-scm-password):  

- способ 1:
    создать в папке проекта файл **release.properties** с 2-мя строками:  
`scm.username=your_github_username`  
`scm.password=your_github_password`  

**Не забудьте добавить файл *release.properties* в .gitignore, вы же не хотите запушить свой логин и пароль на публичный репозиторий :)**

Судя по всему он изменяется с каждой версией, и вышеупомянутые строки придётся вписать ещё раз...

- способ 2:
    дописать параметры при вызове maven: `-Dusername=your_github_username -Dpassword=your_github_password`

12. Не работает release:prepare
-------------------------------

`[ERROR] Failed to execute goal org.apache.maven.plugins:maven-release-plugin:2.4.2:prepare (default-cli) on project Coursework-on-Software-Engineering: Failed to invoke Maven build. Error configuring command-line. Reason: Maven executable not found at: E:\Programming\Examples 9 java\swt\Coursework-on-Software-Engineering\EMBEDDED\bin\mvn.bat -> [Help 1]`  
release-plugin не способен вызвать embedded maven, поэтому придётся [поставить](http://maven.apache.org/download.cgi#Installation_Instructions) maven(да-да, с дописыванием в PATH - чтобы его можно было вызвать из консоли, набрав mvn) и задать его в настройках Eclipse: Window -> Prefrences -> Maven -> Installations  
![](http://img-fotki.yandex.ru/get/9752/165433899.1/0_f38e3_df6f91c3_orig)  
![](http://img-fotki.yandex.ru/get/9747/165433899.1/0_f38e4_e8a50038_orig)  
![](http://img-fotki.yandex.ru/get/9109/165433899.1/0_f38e5_3e67848_orig)  
![](http://img-fotki.yandex.ru/get/9756/165433899.0/0_f38e1_aff1f876_orig)  
![](http://img-fotki.yandex.ru/get/9765/165433899.0/0_f38e0_88d67f3c_orig)  


g.Полезные ссылки<a name="links"></a>
======================================

* <http://markable.in/editor/> - онлайн-редактор Markdown, этот текст был написан в нём
* <http://git-scm.com/book/ru/> - "Pro Git" на русском
* <http://habrahabr.ru/post/77382/> - Apache Maven — основы
* <https://wiki.openmrs.org/display/docs/Using+the+M2Eclipse+Maven+Plugin+in+Eclipse> - работа с m2eclipse
* <http://habrahabr.ru/post/130936/> - Делаем релизы с помощью Maven в Java 
