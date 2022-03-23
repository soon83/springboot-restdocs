package com.soon83.springdatajpa.restdocs;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.operation.preprocess.UriModifyingOperationPreprocessor;

import java.util.List;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;

public interface ApiDocumentUtils {

    static UriModifyingOperationPreprocessor uriModifyingOperationPreprocessor() {
        return modifyUris()
                .scheme("https")
                .host("t-www.campingtalk.me")
                .removePort();
    }

    static List<String> descriptionsForNameProperty(Class clazz, String property) {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(clazz);
        List<String> nameDescription = constraintDescriptions.descriptionsForProperty(property);
        //System.out.println("nameDescription = " + nameDescription);

        return nameDescription;
    }
}
