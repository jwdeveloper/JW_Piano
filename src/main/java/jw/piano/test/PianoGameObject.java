package jw.piano.test;

import jw.piano.service.PianoSkinService;
import jw.fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.fluent_api.minecraft.gameobjects.api.GameObject;
import jw.fluent_api.minecraft.gameobjects.api.ModelRenderer;
import jw.fluent_api.minecraft.tasks.FluentTasks;
import org.bukkit.Particle;

public class PianoGameObject extends GameObject {

    private ModelRenderer modelRenderer;
    private PianoSkinService service;
    private BenchGameObject benchGameObject;
    int x = -360;
    @Override
    public void onCreated() {
        service = FluentInjection.findInjection(PianoSkinService.class);
        modelRenderer = addGameComponent(new ModelRenderer());
        modelRenderer.setCustomModel(service.grandPiano().getItemStack());
        benchGameObject = addGameComponent(new BenchGameObject());


        FluentTasks.taskTimer(1,(iteration, task) ->
        {
           rotate(x);
            x+=3;
            location.getWorld().spawnParticle(Particle.HEART,getLocation(),1);
        }).startAfterIterations(50)
           .run();
    }
}
