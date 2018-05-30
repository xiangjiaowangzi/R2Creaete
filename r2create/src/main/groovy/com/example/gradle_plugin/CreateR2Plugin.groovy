package com.example.gradle_plugin

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class CreateR2Plugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.getExtensions().create("packInfo", PackInfo.class)
    //    LibraryExtension extension = project.extensions.getByType(LibraryExtension.class)
    //    if (extension!=null){
    //      configureR2Generation(this,extension.getLibraryVariants())
    //    }
    project.task("createR2") << {
      createR2Action(project)
    }

    project.afterEvaluate {
      Task assembleDebug = project.tasks.findByPath("assembleDebug")
      Task assembleRelease = project.tasks.findByPath("assembleRelease")
      if (assembleDebug != null) {
        assembleDebug.doLast {
          createR2Action(project)
        }
      }
      if (assembleRelease != null) {
        assembleRelease.doLast {
          createR2Action(project)
        }
      }
    }
  }

  private void createR2Action(Project project) throws Exception {
    log(" R2 is createing wait some times")
    PackInfo packInfo = (PackInfo) project.getExtensions().getByName("packInfo");
    if (packInfo == null) {
      log(" packInfo is null ")
      return
    }
    String builDir = project.getBuildDir().getAbsolutePath()
    String projectgDir = project.getProjectDir()
    String sourcePath = builDir + File.separator +
        "generated" +
        File.separator +
        "source" +
        File.separator +
        "r" +
        File.separator +
        "debug" +
        File.separator

    File file = new File(sourcePath)
    if (!file.exists()) {
      log(" the debug source path is null " + file.getAbsolutePath())
      sourcePath = builDir + File.separator + "generated"
      File.separator + "source"
      File.separator + "r" + File.separator + "release" + File.separator
      if (!file.exists()) {
        log(" the release source path is null " + file.getAbsolutePath())
      }
    }
    String packName = packInfo.packName
    String pathToR = packName.replace(".", File.separator)
    //    log(" packName " + packName)
    //    log(" pathToR " + pathToR)
    String rFilePath = sourcePath + pathToR + File.separator + "R.java"
    String outputPath = projectgDir + File.separator +
        "src" +
        File.separator +
        "main" +
        File.separator +
        "java"
    File outputDir = new File(outputPath)
    File rFile = new File(rFilePath);
    if (!rFile.exists()) {
      log("R.java is not find :" + rFilePath)
      return
    }
    FinalRClassBuilder.brewJava(rFile, outputDir, packName, "R2")
  }

  private void configureR2Generation(Project project, DomainObjectSet variants) {
    for (BaseVariant variant : variants) {
      String outputDir = project.buildDir.resolve("generated/source/r2/${variant.dirName}")
      Task task = project.tasks.create("generate${variant.name.capitalize()}R2")
      task.outputs.dir(outputDir)
      variant.registerJavaGeneratingTask(task, outputDir)
    }
  }

  private void log(String s) {
    System.out.println(s)
  }
}