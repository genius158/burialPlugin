package com.yan.burial;

import com.android.build.api.transform.QualifiedContent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import quinn.hunter.bltransform.RunVariant;

public class BurialExtension {

  /**
   * 那种编译状态下触发
   */
  public RunVariant runVariant = RunVariant.ALWAYS;
  /**
   * 如果不为空，埋点只插入这个配置上的类
   */
  public List<String> foreList = new ArrayList<>();
  /**
   * 这个配置那些不需要插入代码的类
   */
  public List<String> ignoreList = new ArrayList<>();
  /**
   * 作用预
   */
  public List<String> scopes = new ArrayList<>();

  public boolean duplicatedClassSafeMode = false;
  public boolean logEnable = false;
  public boolean listenerWithMethodDetail = true;

  @Override public String toString() {
    return "BurialExtension{" +
        "runVariant=" + runVariant +
        ", whitelist=" + foreList +
        ", blacklist=" + ignoreList +
        ", scopes=" + scopes +
        ", duplicatedClassSafeMode=" + duplicatedClassSafeMode +
        ", logEnable=" + logEnable +
        ", logEnable=" + listenerWithMethodDetail +
        '}';
  }

  /**
   * Only the project content
   * PROJECT(0x01),
   *
   * Only the sub-projects.
   * SUB_PROJECTS(0x04),
   *
   * Only the external libraries
   * EXTERNAL_LIBRARIES(0x10),
   *
   * Code that is being tested by the current variant, including dependencies
   * TESTED_CODE(0x20),
   *
   * Local or remote dependencies that are provided-only
   * PROVIDED_ONLY(0x40),
   */
  Set<QualifiedContent.Scope> getScopes() {
    if (scopes == null || scopes.isEmpty()) return null;
    Set<QualifiedContent.Scope> scopeSet = new HashSet<>();
    for (String s : scopes) {
      if (s == null) continue;
      if (s.equalsIgnoreCase("PROJECT")) {
        scopeSet.add(QualifiedContent.Scope.PROJECT);
      } else if (s.equalsIgnoreCase("SUB_PROJECTS")) {
        scopeSet.add(QualifiedContent.Scope.SUB_PROJECTS);
      } else if (s.equalsIgnoreCase("EXTERNAL_LIBRARIES")) {
        scopeSet.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES);
      } else if (s.equalsIgnoreCase("TESTED_CODE")) {
        scopeSet.add(QualifiedContent.Scope.TESTED_CODE);
      } else if (s.equalsIgnoreCase("PROVIDED_ONLY")) {
        scopeSet.add(QualifiedContent.Scope.PROVIDED_ONLY);
      }
    }
    if (scopeSet.isEmpty()) return null;
    return scopeSet;
  }

  boolean isInWhitelist(String fullQualifiedClassName) {
    boolean inWhiteList = false;
    for (String item : foreList) {
      if (fullQualifiedClassName.contains(item)) {
        inWhiteList = true;
        break;
      }
    }
    return inWhiteList;
  }

  boolean isInBlacklist(String fullQualifiedClassName) {
    boolean inBlacklist = false;
    for (String item : ignoreList) {
      if (fullQualifiedClassName.contains(item)) {
        inBlacklist = true;
        break;
      }
    }
    return inBlacklist;
  }

  static final String PLUGIN_LIBRARY = "com.yan.burial.method.timer";

}
