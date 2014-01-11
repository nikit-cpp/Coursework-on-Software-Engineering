Закрепляем наши наработки в версии 0.1:
(интересные с моей точки зрения решения, котрые хочется сохранить для использования в дальнейших проектах, однако некоторые из них, например, распаковка словаря из .jar, возможно будут выпилены из дальнейших версий для улучшения юзабилити...)

* `engine.filesystem.FileChecker` : отображение MessageBox с предложением распаковать словарь из .jar 
* `engine.filesystem.FileReader` : считывание файла в одну (длинную) строку и дальнейшая передача её в gui
* `engine.thematicdictionary.ThematicDic` : сериализуется и наивно записывается ThematicDicManager'ом в файл dicts.out
* maven-цели:
    - **clean**
    - **compile** -- компиляция
    - **prepare-package** - сборка **.jar** -- для любой платформы, возможен кроссбилдинг, см. [в readme на GitHub](https://github.com/nikit-cpp/Coursework-on-Software-Engineering#crossbuilding)
    - **package** -- сборка  **.exe** / **исполняемого файла**, генерируется launch4j-maven-plugin -- только для своей платформы, кроссбилдинга нет
    - **deploy** -- отправка `*.jar` и `*win32*.exe` на FTP

