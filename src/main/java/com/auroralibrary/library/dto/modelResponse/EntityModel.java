package com.auroralibrary.library.dto.modelResponse;

import org.springframework.data.domain.Page;
import java.util.List;

public class EntityModel<T> {
    private Success<List<T>> success;

    public EntityModel(Page<T> pageData){
        this.success = new Success<>(
            pageData.getContent(),
            new PageInfo(
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages(),
                pageData.getNumber()
            )
        );
    }

    public Success<List<T>> getSuccess(){
        return success;
    }

    public static class Success<T> {
        private T data;
        private PageInfo page;

        public Success(T data, PageInfo page){
            this.data = data;
            this.page = page;
        }

        public PageInfo getPage() {
            return page;
        }

        public T getData() {
            return data;
        }
    }
}
