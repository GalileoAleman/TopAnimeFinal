import com.example.topanime.data.database.SeasonNow
import com.example.topanime.data.database.SeasonNowDao
import com.example.topanime.data.database.TopAnime
import com.example.topanime.data.database.TopAnimeDao
import com.example.topanime.data.database.TopManga
import com.example.topanime.data.database.TopMangaDao
import com.example.topanime.data.server.JikanRestAnimeDbService
import com.example.topanime.data.server.remote.AnimeRemote
import com.example.topanime.data.server.remote.AnimesDb
import com.example.topanime.data.server.remote.Genre
import com.example.topanime.data.server.remote.Images
import com.example.topanime.data.server.remote.Items
import com.example.topanime.data.server.remote.Jpg
import com.example.topanime.data.server.remote.Pagination
import com.example.topanime.data.server.remote.Studio
import com.example.topanime.data.server.remote.Webp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeSeasonNowDao : SeasonNowDao {
    private val seasonsNowList = mutableListOf<SeasonNow>()
    private val seasonNowFlow = MutableStateFlow(seasonsNowList.toList())

    override fun getAllF(): Flow<List<SeasonNow>> = seasonNowFlow

    override suspend fun getAll(): List<SeasonNow> = seasonsNowList.toList()

    override fun findById(id: Long): Flow<SeasonNow> {
        return seasonNowFlow.map { seasons ->
            seasons.first { it.id == id }
        }
    }

    override suspend fun seasonNowCount(): Int = seasonsNowList.size

    override suspend fun insertSeasonsNow(seasonsNow: List<SeasonNow>) {
        seasonsNowList.clear()
        seasonsNowList.addAll(seasonsNow)
        seasonNowFlow.update { seasonsNowList.toList() }
    }

    override suspend fun insertSeasonNow(seasonNow: SeasonNow) {
        val index = seasonsNowList.indexOfFirst { it.id == seasonNow.id }
        if (index != -1) {
            seasonsNowList[index] = seasonNow
        } else {
            seasonsNowList.add(seasonNow)
        }
        seasonNowFlow.update { seasonsNowList.toList() }
    }

    override fun updateSeasonNow(seasonNow: SeasonNow) {
        val index = seasonsNowList.indexOfFirst { it.id == seasonNow.id }
        if (index != -1) {
            seasonsNowList[index] = seasonNow
            seasonNowFlow.update { seasonsNowList.toList() }
        }
    }
}

class FakeTopAnimeDao : TopAnimeDao {
    private val topAnimeList = mutableListOf<TopAnime>()
    private val topAnimeFlow = MutableStateFlow(topAnimeList.toList())

    override fun getAllF(): Flow<List<TopAnime>> = topAnimeFlow

    override suspend fun getAll(): List<TopAnime> = topAnimeList.toList()

    override fun findById(id: Long): Flow<TopAnime> {
        return topAnimeFlow.map { topAnimes ->
            topAnimes.first { it.id == id }
        }
    }

    override suspend fun topAnimeCount(): Int = topAnimeList.size

    override suspend fun insertTopAnimes(topAnimes: List<TopAnime>) {
        topAnimeList.clear()
        topAnimeList.addAll(topAnimes)
        topAnimeFlow.update { topAnimeList.toList() }
    }

    override suspend fun insertTopAnime(topAnime: TopAnime) {
        val index = topAnimeList.indexOfFirst { it.id == topAnime.id }
        if (index != -1) {
            topAnimeList[index] = topAnime
        } else {
            topAnimeList.add(topAnime)
        }
        topAnimeFlow.update { topAnimeList.toList() }
    }

    override fun updateTopAnime(topAnime: TopAnime) {
        val index = topAnimeList.indexOfFirst { it.id == topAnime.id }
        if (index != -1) {
            topAnimeList[index] = topAnime
            topAnimeFlow.update { topAnimeList.toList() }
        }
    }
}

class FakeTopMangaDao : TopMangaDao {
    private val topMangaList = mutableListOf<TopManga>()
    private val topMangaFlow = MutableStateFlow(topMangaList.toList())

    override fun getAllF(): Flow<List<TopManga>> = topMangaFlow

    override suspend fun getAll(): List<TopManga> = topMangaList.toList()

    override fun findById(id: Long): Flow<TopManga> {
        return topMangaFlow.map { topMangas ->
            topMangas.first { it.id == id }
        }
    }

    override suspend fun topMangaCount(): Int = topMangaList.size

    override suspend fun insertTopMangas(topMangas: List<TopManga>) {
        topMangaList.clear()
        topMangaList.addAll(topMangas)
        topMangaFlow.update { topMangaList.toList() }
    }

    override suspend fun insertTopManga(topManga: TopManga) {
        val index = topMangaList.indexOfFirst { it.id == topManga.id }
        if (index != -1) {
            topMangaList[index] = topManga
        } else {
            topMangaList.add(topManga)
        }
        topMangaFlow.update { topMangaList.toList() }
    }

    override fun updateTopManga(topManga: TopManga) {
        val index = topMangaList.indexOfFirst { it.id == topManga.id }
        if (index != -1) {
            topMangaList[index] = topManga
            topMangaFlow.update { topMangaList.toList() }
        }
    }
}

class FakeJikanRestAnimeDbService : JikanRestAnimeDbService {
    override suspend fun getAnimesSeasonNow(page: String): AnimesDb {
        return animesDb
    }

    override suspend fun getTopAnimne(type: String, filter: String, page: String): AnimesDb {
        return animesDb
    }

    override suspend fun getTopManga(filter: String, page: String): AnimesDb {
        return animesDb
    }
}

private val animesDb = AnimesDb(
    pagination = Pagination(
        last_visible_page = 6,
        has_next_page = true,
        items = Items(
            count = 25,
            total = 149,
            per_page = 25
        )
    ),
    data = listOf(
        AnimeRemote(
            id = 55791,
            url = "https://myanimelist.net/anime/55791/Oshi_no_Ko_2nd_Season",
            images = Images(
                jpg = Jpg(
                    image_url = "https://cdn.myanimelist.net/images/anime/1006/143302.jpg",
                    small_image_url = "https://cdn.myanimelist.net/images/anime/1006/143302t.jpg",
                    large_image_url = "https://cdn.myanimelist.net/images/anime/1006/143302l.jpg"
                ),
                webp = Webp(
                    image_url = "https://cdn.myanimelist.net/images/anime/1006/143302.webp",
                    small_image_url = "https://cdn.myanimelist.net/images/anime/1006/143302t.webp",
                    large_image_url = "https://cdn.myanimelist.net/images/anime/1006/143302l.webp"
                )
            ),
            title = "\"Oshi no Ko\" 2nd Season",
            type = "TV",
            source = "Manga",
            episodes = 13,
            status = "Currently Airing",
            duration = "25 min per ep",
            rating = "PG-13 - Teens 13 or older",
            score = 8.45F,
            rank = 159,
            popularity = 858,
            favorites = 3178,
            synopsis = "With the help of producer Masaya Kaburagi, Aquamarine \"Aqua\" Hoshino and Kana Arima have landed the roles of Touki and Tsurugi in Lala Lai Theatrical Company's stage adaptation of the popular manga series Tokyo Blade. Co-starring with them is Aqua's girlfriend, Akane Kurokawa, who plays Touki's fiancée, Princess Saya. Due to the fanbase preferring Tsurugi as Touki's love interest, Saya has made fewer and fewer appearances in the manga, making it difficult for Akane to fully immerse herself in the role. Her struggles are compounded by differences between the play's script and the original work—differences that also greatly frustrate Tokyo Blade's author, Abiko Samejima.\n\nAqua, however, is more concerned with his personal goals than he is with the play. He has only one objective in mind: to grow closer to director Toshirou Kindaichi and find out what he knows about Aqua's mother, Ai.",
            season = "summer",
            year = 2024,
            studios = listOf(
                Studio(type = "anime", name = "Doga Kobo")
            ),
            genres = listOf(
                Genre(type = "anime", name = "Drama"),
                Genre(type = "anime", name = "Supernatural")
            ),
        ),
        AnimeRemote(
            id=5114,
            url = "https://myanimelist.net/anime/5114/Fullmetal_Alchemist__Brotherhood",
            images = Images(
                jpg = Jpg(
                    image_url="https://cdn.myanimelist.net/images/anime/1223/96541.jpg",
                    small_image_url="https://cdn.myanimelist.net/images/anime/1223/96541t.jpg",
                    large_image_url="https://cdn.myanimelist.net/images/anime/1223/96541l.jpg"
                ),
                webp = Webp(
                    image_url = "https://cdn.myanimelist.net/images/anime/1223/96541.webp",
                    small_image_url="https://cdn.myanimelist.net/images/anime/1223/96541t.webp",
                    large_image_url="https://cdn.myanimelist.net/images/anime/1223/96541l.webp"
                )
            ),
            title="Fullmetal Alchemist: Brotherhood",
            type="TV",
            source="Manga",
            episodes=64,
            status="Finished Airing",
            duration="24 min per ep",
            rating="R - 17+ (violence & profanity)",
            score=9.11F,
            rank=1,
            popularity=4,
            favorites=238939,
            synopsis="In order to gain something, you must first lose something of equal value. This is alchemy's first law of Equivalent Exchange—",
            season="spring",
            year=2009,
            studios = listOf(
                Studio(type="anime", name="Bones")
            ),
            genres = listOf(
                Genre(type="anime", name="Action"),
                Genre(type="anime", name="Adventure"),
                Genre(type="anime", name="Drama"),
                Genre(type="anime", name="Fantasy"),
                Genre(type="anime", name="Magic"),
                Genre(type="anime", name="Military"),
                Genre(type="anime", name="Shounen")
            ),
        ),
        AnimeRemote(
            id=430,
            url="https://myanimelist.net/anime/430/Elfen_Lied",
            images = Images(
                jpg=Jpg(
                    image_url="https://cdn.myanimelist.net/images/anime/10/66255.jpg",
                    small_image_url="https://cdn.myanimelist.net/images/anime/10/66255t.jpg",
                    large_image_url="https://cdn.myanimelist.net/images/anime/10/66255l.jpg"
                ),
                webp=Webp(
                    image_url="https://cdn.myanimelist.net/images/anime/10/66255.webp",
                    small_image_url="https://cdn.myanimelist.net/images/anime/10/66255t.webp",
                    large_image_url="https://cdn.myanimelist.net/images/anime/10/66255l.webp"
                )
            ),
            title="Elfen Lied",
            type="TV",
            source="Manga",
            episodes=13,
            status="Finished Airing",
            duration="25 min per ep",
            rating="R+ - Mild Nudity",
            score=7.54F,
            rank=1381,
            popularity=98,
            favorites=9355,
            synopsis="The diclonius, a mutant species homo sapiens, are born with a short pair of horns and possess telekinetic powers—",
            season="summer",
            year=2004,
            studios = listOf(
                Studio(type="anime", name="Arms")
            ),
            genres = listOf(
                Genre(type="anime", name="Action"),
                Genre(type="anime", name="Horror"),
                Genre(type="anime", name="Drama"),
                Genre(type="anime", name="Romance"),
                Genre(type="anime", name="Seinen"),
                Genre(type="anime", name="Supernatural"),
                Genre(type="anime", name="Psychological"),
                Genre(type="anime", name="Tragedy")
            ),
        ),
        AnimeRemote(
            id=49785,
            url="https://myanimelist.net/anime/49785/Fairy_Tail__100-nen_Quest",
            images=Images(
                jpg=Jpg(
                    image_url="https://cdn.myanimelist.net/images/anime/1087/144083.jpg",
                    small_image_url="https://cdn.myanimelist.net/images/anime/1087/144083t.jpg",
                    large_image_url="https://cdn.myanimelist.net/images/anime/1087/144083l.jpg"
                ),
                webp=Webp(
                    image_url="https://cdn.myanimelist.net/images/anime/1087/144083.webp",
                    small_image_url="https://cdn.myanimelist.net/images/anime/1087/144083t.webp",
                    large_image_url="https://cdn.myanimelist.net/images/anime/1087/144083l.webp"
                )
            ),
            title="Fairy Tail: 100-nen Quest",
            type="TV",
            source="Web manga",
            episodes=5,
            status="Currently Airing",
            duration="23 min",
            rating="PG-13 - Teens 13 or older",
            score=8.06F,
            rank=552,
            popularity=1835,
            favorites=1372,
            synopsis="The 100 Years Quest: a mission so challenging and hazardous that it has remained unaccomplished for over a century...",
            season="summer",
            year=2024,
            studios = listOf(
                Studio(type="anime", name="J.C.Staff")
            ),
            genres = listOf(
                Genre(type="anime", name="Action"),
                Genre(type="anime", name="Adventure"),
                Genre(type="anime", name="Fantasy"),
                Genre(type="anime", name="Shounen")
            )
        ),
        AnimeRemote(
            id=58059,
            url="https://myanimelist.net/anime/58059/Tsue_to_Tsurugi_no_Wistoria",
            images = Images(
                jpg = Jpg(
                    image_url="https://cdn.myanimelist.net/images/anime/1281/144104.jpg",
                    small_image_url="https://cdn.myanimelist.net/images/anime/1281/144104t.jpg",
                    large_image_url="https://cdn.myanimelist.net/images/anime/1281/144104l.jpg"
                ),
                webp = Webp(
                    image_url="https://cdn.myanimelist.net/images/anime/1281/144104.webp",
                    small_image_url="https://cdn.myanimelist.net/images/anime/1281/144104t.webp",
                    large_image_url="https://cdn.myanimelist.net/images/anime/1281/144104l.webp"
                )
            ),
            title="Tsue to Tsurugi no Wistoria",
            type="TV",
            source="Manga",
            episodes=7,
            status="Currently Airing",
            duration="23 min",
            rating="PG-13 - Teens 13 or older",
            score=7.94F,
            rank=724,
            popularity=1881,
            favorites=465,
            synopsis="Will Serfort dreams of keeping his promise to a childhood friend by becoming a Magia Vander, one of the mighty magicians who sit atop the Wizard's Tower...",
            season="summer",
            year=2024,
            studios = listOf(
                Studio(type="anime", name="Actas"),
                Studio(type="anime", name="Bandai Namco Pictures")
            ),
            genres = listOf(
                Genre(type="anime", name="Action"),
                Genre(type="anime", name="Adventure"),
                Genre(type="anime", name="Fantasy"),
                Genre(type="anime", name="School"),
                Genre(type="anime", name="Shounen")
            )
        )
    ).shuffled()
)
