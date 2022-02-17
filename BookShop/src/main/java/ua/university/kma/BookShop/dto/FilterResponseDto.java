package ua.university.kma.BookShop.dto;

import lombok.Data;

@Data(staticConstructor = "of")
public class FilterResponseDto {

    public FilterResponseDto(String search) {
        this.search = search;
    }

    public FilterResponseDto() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    private String search;



}
