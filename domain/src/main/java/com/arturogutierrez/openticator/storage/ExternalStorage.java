package com.arturogutierrez.openticator.storage;

import java.io.File;
import java.io.IOException;

public interface ExternalStorage {

  File getExternalStoragePublicFile(String fileName) throws IOException;

}
