package com.intern.design.fileExport;

import com.intern.design.bean.User;

public interface FileExport {

    FileType supportedFileType();

    void export2Db(String filepath);
}
