package com.epam.careers.data;

import com.epam.careers.bo.TestData;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.net.URL;
import java.util.stream.Stream;

public class XmlDataProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        URL dataUrl = this.getClass().getClassLoader().getResource("careers/test_data.xml");
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        TestData testData = xmlMapper.readValue(dataUrl, TestData.class);
        return Stream.of(testData.getSearchCriteria()).map(Arguments::of);
    }
}
