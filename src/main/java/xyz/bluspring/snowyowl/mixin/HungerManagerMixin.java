package xyz.bluspring.snowyowl.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.bluspring.snowyowl.duck.ExtendedHungerManager;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements ExtendedHungerManager {
    @Shadow public abstract void add(int food, float saturationModifier);

    @Shadow public abstract void eat(Item item, ItemStack stack);

    @Override
    public void modifiedEat(@NotNull Item item, @NotNull ItemStack itemStack) {
        if (item.isFood()) {
            FoodComponent foodComponent = item.getFoodComponent();

            if (foodComponent.isMeat()) {
                var registryId = Registries.ITEM.getId(item);
                var cookedId = new Identifier(
                        registryId.getNamespace(),
                        // Usually this helps differentiate between raw and cooked food.
                        // It's basically a hack honestly.
                        "cooked_" + registryId.getPath()
                );

                if (
                        Registries.ITEM.containsId(cookedId)
                ) {
                    // Interesting workaround
                    this.eat(Registries.ITEM.get(cookedId), itemStack);

                    return;
                }
            }

            // Throw it back over to the original function, as this will allow
            // Origins/Apoli to do its own thing with the food.
            this.eat(item, itemStack);
        }
    }
}
