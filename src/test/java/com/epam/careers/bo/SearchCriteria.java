package com.epam.careers.bo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SearchCriteria {
    @JacksonXmlProperty(localName = "search_value")
    private String searchValue;
    private String city;
    private String filter;
    private String sort;
    private String position;

    public SearchCriteria() {
    }

    public SearchCriteria(String searchValue, String city, String filter, String sort, String position) {
        this.searchValue = searchValue;
        this.city = city;
        this.filter = filter;
        this.sort = sort;
        this.position = position;
    }
}
