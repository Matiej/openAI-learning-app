package com.emat.aatranscript_opeinai_app.lector.controllers;

import com.emat.aatranscript_opeinai_app.lector.model.LectorLanguage;

public record RestLectorRequest (String textToRead, LectorLanguage language) {
}
