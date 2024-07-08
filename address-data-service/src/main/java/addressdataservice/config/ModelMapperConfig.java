package addressdataservice.config;

import addressdataservice.dto.AddressDetails;
import addressdataservice.dto.AddressResponse;
import addressdataservice.entity.AddressData;
import addressdataservice.entity.UserAddress;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    Converter<AddressData, AddressDetails> addressDataToDetailsConverter = new Converter<AddressData, AddressDetails>() {
      @Override
      public AddressDetails convert(MappingContext<AddressData, AddressDetails> context) {
        AddressData source = context.getSource();
//        AddressDetails destination = new AddressDetails();
        return modelMapper.map(source, AddressDetails.class);
//        destination.setId(source.getId());
//        destination.setName(source.getName());
//        destination.setType(source.getType());
//        destination.setCode(source.getCode());
//        destination.setParentCode(source.getParentCode());
//        return destination;
      }
    };
    modelMapper.addMappings( new PropertyMap<UserAddress, AddressResponse>() {
      protected void configure() {
        using(addressDataToDetailsConverter).map(source.getWard(), destination.getWard());
        using(addressDataToDetailsConverter).map(source.getDistrict(), destination.getDistrict());
        using(addressDataToDetailsConverter).map(source.getProvince(), destination.getProvince());
      }
    });

    return modelMapper;
  }

  ;

}
