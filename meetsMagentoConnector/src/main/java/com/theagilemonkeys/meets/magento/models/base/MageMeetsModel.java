package com.theagilemonkeys.meets.magento.models.base;

import com.theagilemonkeys.meets.magento.utils.soap.SoapSerializableObject;
import com.theagilemonkeys.meets.models.base.AbstractMeetsModel;
import com.theagilemonkeys.meets.models.base.MeetsModel;
import org.ksoap2.serialization.KvmSerializable;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
@SuppressWarnings("unchecked")
public abstract class MageMeetsModel<MODEL extends MeetsModel> extends AbstractMeetsModel<MODEL> implements KvmSerializable {
    private SoapSerializableObject serializableObject = new SoapSerializableObject(this);
    implement!
}
