package com.lggyx.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@RestController
@RequestMapping("/assessment")
@Tag(name = "测评记录接口",description = "评测接口")
public class AssessmentController {

}

