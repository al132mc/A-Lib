package al132.alib.client

import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.fluids.IFluidTank
import java.util.*

/**
 * Created by al132 on 4/14/2017.
 */



abstract class CapabilityDisplayWrapper(val x: Int, val y: Int, val width: Int, val height: Int) {

    abstract fun getStored(): Int
    abstract fun getCapacity(): Int
    abstract fun toStringList(): List<String>
}

//==
open class CapabilityFluidDisplayWrapper(x: Int, y: Int, width: Int, height: Int,  val fluidTank: () -> IFluidTank) :
        CapabilityDisplayWrapper(x, y, width, height) {

    override fun getCapacity() = fluidTank().capacity
    override fun getStored() = fluidTank().fluidAmount

    override fun toStringList() = arrayListOf("${getStored()}/${getCapacity()} mb ${fluidTank().fluid?.fluid?.name ?: ""}")

    fun getFluid() = fluidTank().fluid
}

//==
open class CapabilityEnergyDisplayWrapper(x: Int, y: Int, width: Int, height: Int, val energyStorage: () -> IEnergyStorage) :
        CapabilityDisplayWrapper(x, y, width, height) {

    override fun getStored() = energyStorage().energyStored
    override fun getCapacity() = energyStorage().maxEnergyStored

    val numFormat = java.text.NumberFormat.getInstance(Locale.US)

    override fun toStringList(): List<String> {
        val stored = numFormat.format(getStored())
        val capacity = numFormat.format(getCapacity())
        return arrayListOf("$stored/$capacity FE")
    }
}