package jw.piano.test;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.service.PianoSkinService;
import jw.fluent.api.spigot.gameobjects.api.GameObject;
import jw.fluent.api.spigot.gameobjects.api.ModelRenderer;
import jw.fluent.api.spigot.tasks.FluentTasks;
import org.bukkit.Particle;

public class PianoGameObject extends GameObject {

    private ModelRenderer modelRenderer;
    private PianoSkinService service;
    private BenchGameObject benchGameObject;
    int x = -360;
    @Override
    public void onCreated() {
        service = FluentApi.injection().findInjection(PianoSkinService.class);
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
