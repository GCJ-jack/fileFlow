package com.intern.design.fileExport;

import com.intern.design.bean.User;

public interface FileExport {

    void Export2Db(String string);

    FileType supportedFileType();
}
