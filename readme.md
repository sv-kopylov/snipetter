## Разметка текстов по признакам  

Версия gradle - 4.10.2
В проекте нужно создать папку db (также как и в корне, где лежит jar)


Структура программы:
ru.kopylov.snippeter.view.App - Точка входа  
ru.kopylov.snippeter.context.Context - класс в котором хранятся компоненты  
ru.kopylov.snippeter.context.ApplicationInitializer - класс в котором первоначально создаются компонеты и добавляются в контекст (порядок важен)    
ru.kopylov.snippeter.utils.EntityManagerHolder -  Синглтон управляет етити менеджером, и предоставляет к нему доступ  


