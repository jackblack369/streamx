/*
 * Copyright (c) 2019 The StreamX Project
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.streamxhub.streamx.flink.packer.pipeline

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Result of a BuildPipeline instance.
 *
 * @author Al-assad
 */
sealed trait BuildResult {

  /**
   * is pass aka is successfully
   */
  def pass: Boolean

  def as[T <: BuildResult](clz: Class[T]): T = this.asInstanceOf[T]
}

sealed trait FlinkBuildResult extends BuildResult {
  def workspacePath: String
}

sealed trait FlinkSessionBuildResult extends FlinkBuildResult {
  def flinkShadedJarPath: String
}


@JsonIgnoreProperties(ignoreUnknown = true)
case class ErrorResult(pass: Boolean = false) extends BuildResult {
}

@JsonIgnoreProperties(ignoreUnknown = true)
case class FlinkK8sSessionBuildResponse(workspacePath: String,
                                        flinkShadedJarPath: String,
                                        pass: Boolean = true) extends FlinkSessionBuildResult

@JsonIgnoreProperties(ignoreUnknown = true)
case class FlinkK8sApplicationBuildResponse(workspacePath: String,
                                            flinkImageTag: String,
                                            podTemplatePaths: Map[String, String],
                                            dockerInnerMainJarPath: String,
                                            pass: Boolean = true) extends FlinkBuildResult

// todo case class FlinkYarnSessionBuildResponse(workspacePath: String, flinkShadedJarPath: String) extends FlinkSessionBuildResult

// todo case class FlinkYarnApplicationBuildResponse() extends BuildResult

// todo case class FlinkStandaloneBuildResponse(workspacePath: String, flinkShadedJarPath: String) extends FlinkSessionBuildResult



