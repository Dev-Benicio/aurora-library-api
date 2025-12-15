package com.auroralibrary.library.dto.modelResponse;

public class EntityModelSingle<T> {
    private Success<T> success;

    public EntityModelSingle(T singleData){
        this.success = new Success<>(
                singleData,
            new PageInfo(1,1L,1,0)
        );
    }

    public Success<T> getSuccess(){
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
