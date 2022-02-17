package ua.university.kma.BookShop.dto;

public class AddResponseDto {

    public AddResponseDto() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String response;

    public AddResponseDto(String response){
        this.response = response;
    }
}
