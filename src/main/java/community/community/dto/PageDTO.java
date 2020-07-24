package community.community.dto;

import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> {
    private List<T> DTOList;
    private boolean showPre;
    private boolean showFirst;
    private boolean showLast;
    private boolean showNext;
    private Integer pageNum;
    private Integer currPageNum;
    private List<Integer> pages = new ArrayList<>();

    public List<T> getDTOList() {
        return DTOList;
    }

    public void setDTOList(List<T> DTOList) {
        this.DTOList = DTOList;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public boolean isShowPre() {
        return showPre;
    }

    public void setShowPre(boolean showPre) {
        this.showPre = showPre;
    }

    public boolean isShowFirst() {
        return showFirst;
    }

    public void setShowFirst(boolean showFirst) {
        this.showFirst = showFirst;
    }

    public boolean isShowLast() {
        return showLast;
    }

    public void setShowLast(boolean showLast) {
        this.showLast = showLast;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getCurrPageNum() {
        return currPageNum;
    }

    public void setCurrPageNum(Integer currPageNum) {
        this.currPageNum = currPageNum;
    }

    public void setPage(Integer count, Integer page, Integer size) {
        currPageNum = page;
        pageNum = count / size;
        if (pageNum == 0) {
            showPre = false;
            showFirst = false;
            showLast = false;
            showNext = false;
            pages.add(1);
            return;
        }
        else if (count % size != 0) {
            pageNum++;
            showNext = true;
        }

        for (int i = size - 1; i >= 0; --i) {
            int curr = currPageNum - i + size / 2;
            if (curr > 0 && curr <= pageNum) {
                pages.add(curr);
            }
        }
        showPre = (page == 1) ? false : true;
        showNext = (page == pageNum ) ? false : true;
        showFirst = (page > 1 ) ? true : false;
        showLast = (page < pageNum) ? true : false;
    }
}
