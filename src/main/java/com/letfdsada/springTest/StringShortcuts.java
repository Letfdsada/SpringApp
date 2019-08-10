package com.letfdsada.springTest;

import java.nio.file.Paths;

public class StringShortcuts {
    /*
    возвращает полный путь к каталогу на основе относительного с удалением подстроки "~\"
    relativePath представляет собой строку шаблона "~/*"
    TODO: проверить, возможен ли доступ к относительному пути по шаблону "~/"
     */
    public static String getRelativeToAbsolutePath(String relativePath){
        return Paths.get(relativePath).toAbsolutePath().toString()
                .replace("~\\", "");
    }
}
