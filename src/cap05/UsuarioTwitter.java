package cap05;

import java.util.ArrayList;

public class UsuarioTwitter {
	
	//Atributos
	private String id; //identificador único de cada usuario
	private String screenName; //nick de twitter (sin la arroba)
	private ArrayList<String> tags; //lista de etiquetas
	private String avatar; //URL del gráfico del avatar de ese usuario
	private long followersCount; //número de seguidores
	private long friendsCount; //número de amigos
	private String lang; //idioma
	private long lastSeen; //fecha de última entrada en twitter (en milisegundos desde 1/1/1970)
	private String tweetId; //identificador de tuit
	private ArrayList<String> friends;// lista de amigos (expresados como ids de usuarios)
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public long getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}
	public long getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public long getLastSeen() {
		return lastSeen;
	}
	public void setLastSeen(long lastSeen) {
		this.lastSeen = lastSeen;
	}
	public String getTweetId() {
		return tweetId;
	}
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}
	public ArrayList<String> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	
	public UsuarioTwitter() {
		this.id = "";
		this.screenName = "";
		this.tags = new ArrayList<>();
		this.avatar = "";
		this.followersCount = 0;
		this.friendsCount = 0;
		this.lang = "";
		this.lastSeen = 0;
		this.tweetId = "";
		this.friends = new ArrayList<>();

		}
	
	public UsuarioTwitter(String id, String screenName, ArrayList<String> tags, String avatar, long followersCount,
			long friendsCount, String lang, long lastSeen, String tweetId, ArrayList<String> friends) {
		super();
		this.id = id;
		this.screenName = screenName;
		this.tags = tags;
		this.avatar = avatar;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.lang = lang;
		this.lastSeen = lastSeen;
		this.tweetId = tweetId;
		this.friends = friends;
	}
	@Override
	public String toString() {
		return "UsuarioTwitter [id=" + id + ", screenName=" + screenName + ", tags=" + tags + ", avatar=" + avatar
				+ ", followersCount=" + followersCount + ", friendsCount=" + friendsCount + ", lang=" + lang
				+ ", lastSeen=" + lastSeen + ", tweetId=" + tweetId + ", friends=" + friends + "]";
	}
	
}
