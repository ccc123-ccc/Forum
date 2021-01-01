package com.example.demo.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PagesDTO<T> {
    private List<T> data = new ArrayList<> ();
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private List<Integer> pages=new ArrayList<> ();
    private Integer page;
    private Integer totalPages;

    public void setPages (Integer page, Integer size, Integer totalCount) {

        if (totalCount % size == 0) {
            totalPages = totalCount / size;
        } else {
            totalPages = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        this.page = page;
        pages.add (page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add (0, page - i);
            }
            if (page + i <=totalPages) {
                pages.add (page + i);
            }
        }
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        if (page == totalPages) {
            showNext = false;
        } else {
            showNext = true;
        }
        if (pages.contains (1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        if (pages.contains (totalPages)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }

}
