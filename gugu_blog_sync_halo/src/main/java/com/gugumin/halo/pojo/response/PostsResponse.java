package com.gugumin.halo.pojo.response;

import com.gugumin.pojo.Article;
import com.gugumin.pojo.Meta;
import com.gugumin.pojo.MetaType;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Posts response.
 *
 * @author minmin
 * @date 2023 /03/08
 */
@Data
public class PostsResponse {
    private Integer id;
    private String title;
    private String status;
    private String slug;
    private String editorType;
    private Long updateTime;
    private Long createTime;
    private Long editTime;
    private Object metaKeywords;
    private Object metaDescription;
    private String fullPath;
    private String summary;
    private String thumbnail;
    private Integer visits;
    private Boolean disallowComment;
    private String password;
    private String template;
    private Integer topPriority;
    private Integer likes;
    private Integer wordCount;
    private Boolean inProgress;
    private String originalContent;
    private String content;
    private Integer commentCount;
    private List<Integer> tagIds;
    private List<Tags> tags;
    private List<Integer> categoryIds;
    private List<Categories> categories;
    private List<?> metaIds;
    private List<?> metas;
    private String formatContent;
    private Boolean topped;

    /**
     * To article article.
     *
     * @return the article
     */
    public Article toArticle(MetaType metaType) {
        Meta meta = new Meta();
        Map<Integer, Categories> categoriesMap = categories.stream().collect(Collectors.toMap(Categories::getId, item -> item));
        meta.setCategories(categories.stream().map(item -> {
            Meta.Category category = new Meta.Category();
            category.setName(item.getName());
            if (Objects.nonNull(item.getParentId())) {
                Categories parent = categoriesMap.get(item.getParentId());
                Optional.ofNullable(parent).ifPresent(parentItem -> category.setParent(parentItem.name));
            }
            return category;
        }).collect(Collectors.toList()));
        meta.setTags(tags.stream().map(item -> {
            Meta.Tag tag = new Meta.Tag();
            tag.setName(item.getName());
            return tag;
        }).collect(Collectors.toList()));
        meta.setSummary(summary);
        return new Article(title, originalContent, meta, metaType);
    }

    /**
     * The type Tags.
     */
    @Data
    public static class Tags {
        private Integer id;
        private String name;
        private String slug;
        private String color;
        private String thumbnail;
        private Long createTime;
        private String fullPath;
    }

    /**
     * The type Categories.
     */
    @Data
    public static class Categories {
        private Integer id;
        private String name;
        private String slug;
        private String description;
        private String thumbnail;
        private Integer parentId;
        private Object password;
        private Long createTime;
        private String fullPath;
        private Integer priority;
    }
}
