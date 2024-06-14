package com.emat.aatranscript_opeinai_app.truckadvisor.services;

import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvAnswer;
import com.emat.aatranscript_opeinai_app.truckadvisor.model.TruckAdvQuestion;

public interface TruckAdvService {

    TruckAdvAnswer getBasicAdvice(TruckAdvQuestion question);
}
