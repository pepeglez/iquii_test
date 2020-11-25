package com.example.iquiitest.model


import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("kind")
    val kind: String
) {
    data class Data(
        @SerializedName("children")
        val children: List<Children>,
        @SerializedName("dist")
        val dist: Int,
        @SerializedName("modhash")
        val modhash: String
    ) {
        data class Children(
            @SerializedName("data")
            val `data`: Data,
            @SerializedName("kind")
            val kind: String
        ) {
            data class Data(
                @SerializedName("author")
                val author: String,
                @SerializedName("created_utc")
                val createdUtc: Double,
                @SerializedName("preview")
                val preview: Preview,
                @SerializedName("thumbnail")
                val thumbnail: String,
                @SerializedName("title")
                val title: String,
                @SerializedName("url")
                val url: String,
                @SerializedName("url_overridden_by_dest")
                val urlOverriddenByDest: String
            ) {
                data class Preview(
                    @SerializedName("enabled")
                    val enabled: Boolean,
                    @SerializedName("images")
                    val images: List<Image>
                ) {
                    data class Image(
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("resolutions")
                        val resolutions: List<Resolution>,
                        @SerializedName("source")
                        val source: Source,
                        @SerializedName("variants")
                        val variants: Variants
                    ) {
                        data class Resolution(
                            @SerializedName("height")
                            val height: Int,
                            @SerializedName("url")
                            val url: String,
                            @SerializedName("width")
                            val width: Int
                        )

                        data class Source(
                            @SerializedName("height")
                            val height: Int,
                            @SerializedName("url")
                            val url: String,
                            @SerializedName("width")
                            val width: Int
                        )

                        class Variants(
                        )
                    }
                }
            }
        }
    }
}