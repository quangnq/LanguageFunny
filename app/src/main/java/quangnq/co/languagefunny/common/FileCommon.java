package quangnq.co.languagefunny.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by QUANGNQ on 12/16/2017.
 */
public class FileCommon {

  public static ArrayList<String> getListFileName(String path) {
    File file = new File(path);
    File[] files = file.listFiles(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.isFile();
      }
    });
    ArrayList<String> list = new ArrayList<>();
    for (int i = 0; i < files.length; i++) {
      list.add(files[i].getName());
    }
    return list;
  }
  
  public static ArrayList<String> getListFilePath(String path) {
    File file = new File(path);
    File[] files = file.listFiles(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.isFile();
      }
    });
    ArrayList<String> list = new ArrayList<>();
    for (int i = 0; i < files.length; i++) {
      list.add(files[i].getPath());
    }
    return list;
  }
  
  public static ArrayList<String> getListFolderName(String path) {
    File file = new File(path);
    File[] files = file.listFiles(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.isDirectory();
      }
    });
    ArrayList<String> list = new ArrayList<>();
    for (int i = 0; i < files.length; i++) {
      list.add(files[i].getName());
    }
    return list;
  }

  public static ArrayList<String> getListFolderPath(String path) {
    File file = new File(path);
    File[] files = file.listFiles(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.isDirectory();
      }
    });
    ArrayList<String> list = new ArrayList<>();
    for (int i = 0; i < files.length; i++) {
      list.add(files[i].getPath());
    }
    return list;
  }
  
  public static ArrayList<String> readFile(String path) {
    File file = new File(path);
    BufferedReader reader = null;
    ArrayList<String> list = new ArrayList<>();
    try {
      String line = "";
      String[] arrs;
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
      while ((line = reader.readLine()) != null) {
        if (!"".equals(line.trim())) {
          list.add(line.trim());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null)
          reader.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }
}
