package com.naemo.afriscout.views.activities.pages.playerstats.allstats

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.afriscout.R
import com.naemo.afriscout.db.local.room.search.Data
import com.naemo.afriscout.db.local.room.search.SearchRepository
import com.naemo.afriscout.db.local.room.stats.PlayerStats
import com.naemo.afriscout.db.local.room.stats.Stats
import com.naemo.afriscout.db.local.room.stats.StatsRepository
import com.naemo.afriscout.views.base.BaseViewModel
import dagger.Module
import dagger.Provides

class AllStatsViewModel(application: Application): BaseViewModel<AllStatsNavigator>(application) {

    var image = ObservableField<String>()
    var age = ObservableField("")
    var nation = ObservableField("")
    var position = ObservableField("")
    var appearances = ObservableField("")
    var assists = ObservableField("")
    var blocks = ObservableField("")
    var captain = ObservableField("")
    var totalCrosses = ObservableField("")
    var accurateCrosses = ObservableField("")
    var dispossessed = ObservableField("")
    var attemptedDribbles = ObservableField("")
    var pastDribbles = ObservableField("")
    var successfulDribbles = ObservableField("")
    var totalDuels = ObservableField("")
    var duelsWon = ObservableField("")
    var foulsCommitted = ObservableField("")
    var foulsDrawn = ObservableField("")
    var goals = ObservableField("")
    var hitPost = ObservableField("")
    var insideBoxSaves = ObservableField("")
    var interceptions = ObservableField("")
    var minutes = ObservableField("")
    var totalPasses = ObservableField("")
    var keyPasses = ObservableField("")
    var accuratePasses = ObservableField("")
    var penaltiesWon = ObservableField("")
    var penaltiesCommitted = ObservableField("")
    var penaltiesMissed = ObservableField("")
    var penaltiesSaved = ObservableField("")
    var penaltiesScored = ObservableField("")
    var redCards = ObservableField("")
    var yellowCards = ObservableField("")
    var doubleYellow = ObservableField("")
    var subbedIn = ObservableField("")
    var subbedOut = ObservableField("")
    var bench = ObservableField("")
    var tackles = ObservableField("")
    var saves = ObservableField("")

    private val appsArray = mutableListOf<Int>()
    private val assistsArray = mutableListOf<Int>()
    private val blocksArray = mutableListOf<Int>()
    private val capsArray = mutableListOf<Int>()
    private val totalCountArray = mutableListOf<Int>()
    private val accurateCrossesArray = mutableListOf<Int>()
    private val dispossessedArray = mutableListOf<Int>()
    private val attDribblesArray = mutableListOf<Int>()
    private val pastDribblesArray = mutableListOf<Int>()
    private val successDribblesArray = mutableListOf<Int>()
    private val duelsTotalArray = mutableListOf<Int>()
    private val duelsWonArray = mutableListOf<Int>()
    private val foulsDrawnArray = mutableListOf<Int>()
    private val foulsCommittedArray = mutableListOf<Int>()
    private val goalsArray = mutableListOf<Int>()
    private val postArray = mutableListOf<Int>()
    private val boxArray = mutableListOf<Int>()
    private val interArray = mutableListOf<Int>()
    private val minArray = mutableListOf<Int>()
    private val accPassesArray = mutableListOf<Int>()
    private val keyPassesArray = mutableListOf<Int>()
    private val totalPassesArray = mutableListOf<Int>()
    private val committedPenalties = mutableListOf<Int>()
    private val missedPenalties = mutableListOf<Int>()
    private val pkSavesArray = mutableListOf<Int>()
    private val pkWonArray = mutableListOf<Int>()
    private val penaltyScoredArray = mutableListOf<Int>()
    private val redCardsArray = mutableListOf<Int>()
    private val yellowCardsArray = mutableListOf<Int>()
    private val doubleYellowArray = mutableListOf<Int>()
    private val savesArray = mutableListOf<Int>()
    private val subInArray = mutableListOf<Int>()
    private val subOutArray = mutableListOf<Int>()
    private val tacklesArray = mutableListOf<Int>()



    private var repository: StatsRepository? = null
    private var searchRepository: SearchRepository? = null

    init {
        repository = StatsRepository(application)
        searchRepository = SearchRepository(application)
    }

    fun setStats(playerStats: List<PlayerStats>?) {
        playerStats?.let { stats ->
            for (i in stats) {
                val apps = i.appearences
                apps?.let { appsArray.add(it) }
                val assists = i.assists
                assists?.let { assistsArray.add(it) }
                val blocks = i.blocks.toString()
                blocks.toIntOrNull()?.let { blocksArray.add(it) }
                val captain = i.captain
                captain?.let { capsArray.add(it) }
                val totalC = i.crosses?.crossesTotal.toString()
                totalC.toIntOrNull()?.let { totalCountArray.add(it) }
                val accC = i.crosses?.accurate.toString()
                accC.toIntOrNull()?.let { accurateCrossesArray.add(it) }
                val dis = i.dispossesed
                dis?.let { dispossessedArray.add(it) }
                val attD = i.dribbles?.attempts.toString()
                attD.toIntOrNull()?.let { attDribblesArray.add(it) }
                val pastD = i.dribbles?.dribbledPast.toString()
                pastD.toIntOrNull()?.let { pastDribblesArray.add(it) }
                val succD = i.dribbles?.success.toString()
                succD.toIntOrNull()?.let { successDribblesArray.add(it) }
                val duelsT = i.duels?.duelsTotal.toString()
                duelsT.toIntOrNull()?.let { duelsTotalArray.add(it) }
                val duelsW = i.duels?.duelsWon.toString()
                duelsW.toIntOrNull()?.let { duelsWonArray.add(it) }
                val foulsD = i.fouls?.drawn.toString()
                foulsD.toIntOrNull()?.let { foulsDrawnArray.add(it) }
                val foulsC = i.fouls?.foulsCommitted.toString()
                foulsC.toIntOrNull()?.let { foulsCommittedArray.add(it) }
                val goals = i.goals
                goals?.let { goalsArray.add(it) }
                val post = i.hitPost.toString()
                post.toIntOrNull()?.let { postArray.add(it) }
                val box  = i.insideBoxSaves
                box?.let { boxArray.add(it) }
                val inter = i.interceptions
                inter?.let { interArray.add(it) }
                val min = i.minutes
                min?.let { minArray.add(it) }
                val accPasses = i.passes?.accuracy.toString()
                accPasses.toIntOrNull()?.let { accPassesArray.add(it) }
                val kPasses = i.passes?.keyPasses.toString()
                kPasses.toIntOrNull()?.let { keyPassesArray.add(it) }
                val tPasses = i.passes?.passTotal.toString()
                tPasses.toIntOrNull()?.let { totalPassesArray.add(it) }
                val comPen = i.penalties?.committed.toString()
                comPen.toIntOrNull()?.let { committedPenalties.add(it) }
                val missPen = i.penalties?.missed.toString()
                missPen.toIntOrNull()?.let { missedPenalties.add(it) }
                val pkSaves = i.penalties?.pkSaves.toString()
                pkSaves.toIntOrNull()?.let { pkSavesArray.add(it) }
                val wPk = i.penalties?.won.toString()
                wPk.toIntOrNull()?.let { pkWonArray.add(it) }
                val sPl = i.penalties?.scores.toString()
                sPl.toIntOrNull()?.let { penaltyScoredArray.add(it) }
                val redCards = i.redcards
                redCards?.let { redCardsArray.add(it) }
                val yellowCards = i.yellowcards
                yellowCards?.let { yellowCardsArray.add(it) }
                val doubleYellow = i.yellowred
                doubleYellow?.let { doubleYellowArray.add(it) }
                val saves = i.saves
                saves?.let { savesArray.add(it) }
                val subIn = i.substituteIn
                subIn?.let { subInArray.add(it) }
                val subOut = i.substituteOut
                subOut?.let { subOutArray.add(it) }
                val tackles = i.tackles.toString()
                tackles.toIntOrNull()?.let { tacklesArray.add(it) }
            }
        }

        Log.d("aps", appsArray.toString())
        Log.d("aps1", appsArray.sum().toString())
        val a = appsArray.sum()
        val b = assistsArray.sum()
        val c = blocksArray.sum()
        val d = capsArray.sum()
        val e = totalCountArray.sum()
        val f = accurateCrossesArray.sum()
        val g = dispossessedArray.sum()
        val h = attDribblesArray.sum()
        val i = pastDribblesArray.sum()
        val j = successDribblesArray.sum()
        val k = duelsTotalArray.sum()
        val l = duelsWonArray.sum()
        val m = foulsCommittedArray.sum()
        val n = foulsDrawnArray.sum()
        val o = goalsArray.sum()
        val p = postArray.sum()
        val q = boxArray.sum()
        val r = interArray.sum()
        val s = minArray.sum()
        val t = accPassesArray.sum()
        val u = keyPassesArray.sum()
        val v = totalPassesArray.sum()
        val w = committedPenalties.sum()
        val x = missedPenalties.sum()
        val y = penaltyScoredArray.sum()
        val z = redCardsArray.sum()
        val az = yellowCardsArray.sum()
        val bz = doubleYellowArray.sum()
        val cz = savesArray.sum()
        val dz = subInArray.sum()
        val ez = subOutArray.sum()
        val fy = tacklesArray.sum()

        Log.d("aps2", a.toString())
        appearances.set(a.toString())
        assists.set(b.toString())
        blocks.set(c.toString())
        captain.set(d.toString())
        totalCrosses.set(e.toString())
        accurateCrosses.set(f.toString())
        dispossessed.set(g.toString())
        attemptedDribbles.set(h.toString())
        pastDribbles.set(i.toString())
        successfulDribbles.set(j.toString())
        totalDuels.set(k.toString())
        duelsWon.set(l.toString())
        foulsCommitted.set(m.toString())
        foulsDrawn.set(n.toString())
        goals.set(o.toString())
        hitPost.set(p.toString())
        insideBoxSaves.set(q.toString())
        interceptions.set(r.toString())
        minutes.set(s.toString())
        accuratePasses.set(t.toString())
        keyPasses.set(u.toString())
        totalPasses.set(v.toString())
        penaltiesCommitted.set(w.toString())
        penaltiesMissed.set(x.toString())
        penaltiesScored.set(y.toString())
        redCards.set(z.toString())
        yellowCards.set(az.toString())
        doubleYellow.set(bz.toString())
        saves.set(cz.toString())
        subbedIn.set(dz.toString())
        subbedOut.set(ez.toString())
        tackles.set(fy.toString())
    }

    fun getPlayerBio(playerId: Int): LiveData<Data>? {
        return searchRepository?.loadPlayerData(playerId)
    }

    fun setBio(img: String?, playerAge: String?, nationality: String?, playerPosition: String?) {
        image.set(img)
        age.set(playerAge)
        nation.set(nationality)
        position.set(playerPosition)
    }

    fun getStats(id: Int): LiveData<Stats>? {
        return repository?.loadOne(id)
    }

}

interface AllStatsNavigator {

    fun goBack()
}

@Module
class AllStatsModule {
    @Provides
    fun provideAllStatsViewModel(application: Application): AllStatsViewModel {
        return AllStatsViewModel(application)
    }

    @Provides
    fun layoutId() : Int {
        return R.layout.activity_all_stats
    }
}