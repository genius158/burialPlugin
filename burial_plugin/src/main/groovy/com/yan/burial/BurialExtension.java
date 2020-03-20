package com.yan.burial;

import com.android.build.api.transform.QualifiedContent;
import com.quinn.hunter.transform.RunVariant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by quinn on 27/06/2017.
 * whitelist is prior to to blacklist
 */
public class BurialExtension {

  public RunVariant runVariant = RunVariant.ALWAYS;
  public List<String> whitelist = new ArrayList<>();
  public List<String> blacklist = new ArrayList<>();
  public List<String> scopes = new ArrayList<>();
  public boolean duplicatedClassSafeMode = false;
  public boolean logEnable = false;

  @Override public String toString() {
    return "BurialExtension{" +
        "runVariant=" + runVariant +
        ", whitelist=" + whitelist +
        ", blacklist=" + blacklist +
        ", scopes=" + scopes +
        ", duplicatedClassSafeMode=" + duplicatedClassSafeMode +
        ", logEnable=" + logEnable +
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
  public Set<QualifiedContent.Scope> getScopes() {
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
}
