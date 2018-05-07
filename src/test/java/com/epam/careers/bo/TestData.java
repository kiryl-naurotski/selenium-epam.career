package com.epam.careers.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@JacksonXmlRootElement(localName = "test_data")
public final class TestData {
    @Getter
    @Setter
    @JacksonXmlProperty(localName = "search_criteria")
    private SearchCriteria[] searchCriteria;

    public TestData() {
    }

    public TestData(SearchCriteria[] searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
