import android.os.Parcelable
import com.unludev.rickandmorty.data.model.character.Location
import com.unludev.rickandmorty.data.model.character.Origin
import kotlinx.parcelize.Parcelize

@Parcelize
data class RickAndMortyCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
) : Parcelable
