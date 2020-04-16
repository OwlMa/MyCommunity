package community.community.dto;

import java.util.ArrayList;
import java.util.List;

public class PageDTO {
    private List<ArticleDTO> articleDTOList;
    private boolean showPre;
    private boolean showFirst;
    private boolean showLast;
    private boolean showNext;
    private Integer pageNum;
    private Integer currPageNum;
    private List<Integer> pages = new ArrayList<>();

    public List<ArticleDTO> getArticleDTOList() {
        return this.articleDTOList;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setArticleDTOList(List<ArticleDTO> articleDTOList) {
        this.articleDTOList = articleDTOList;
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
        else {
            pageNum++;
        }

        if (pageNum - page >= 2 && page > 2) {
            for (int i = -2; i <=2; ++i) {
                pages.add(page+i);
            }
        }
        else if (page > 2) {
            for (int i = Math.min(pageNum, 5)-1; i >= 0; --i) {
                pages.add(pageNum-i);
            }
        }
        else {
            for (int i = 0; i < Math.min(pageNum, 5); ++i) {
                pages.add(i+1);
            }
        }

        showPre = (page == 1) ? false : true;
        showNext = (page == pageNum ) ? false : true;
        showFirst = pages.contains(1) ? false : true;
        showLast = pages.contains(pageNum) ? false : true;


    }
}
