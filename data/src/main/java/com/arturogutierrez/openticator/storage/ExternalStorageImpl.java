package com.arturogutierrez.openticator.storage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;

public class ExternalStorageImpl implements ExternalStorage {

  @Inject
  public ExternalStorageImpl() {

  }

  @Override
  public File getExternalStoragePublicFile(String fileName) throws IOException {
    File publicDirectory;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      publicDirectory = getExternalStoragePublicDocumentsDirectory();
    } else {
      publicDirectory = getExternalStorageDocumentsDirectory();
    }

    boolean isPresent = publicDirectory.exists();
    if (!isPresent && !publicDirectory.mkdirs()) {
      throw new IOException("Unable to create app directory in external storage");
    }

    return new File(publicDirectory, fileName);
  }

  @TargetApi(Build.VERSION_CODES.KITKAT)
  private File getExternalStoragePublicDocumentsDirectory() {
    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
  }

  private File getExternalStorageDocumentsDirectory() {
    return new File(Environment.getExternalStorageDirectory() + "/Documents");
  }
}
