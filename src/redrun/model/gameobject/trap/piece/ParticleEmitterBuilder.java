package redrun.model.gameobject.trap.piece;

import org.lwjgl.util.vector.Vector3f;



public class ParticleEmitterBuilder {

    private Vector3f location = new Vector3f(0, 0, 0);
    private float spawningRate = 3;
    private int particleLifeTime = 300;
    private Vector3f gravity = new Vector3f(0, -0.0003f, 0);
    private boolean enable3D = false;
    private Vector3f initialVelocity = new Vector3f(0, 0, 0);
    private float velocityModifier = 1.0f;

    /**
     * Sets the location of the particle emitter.
     *
     * @param location the location of the particle emitter
     */
    public ParticleEmitterBuilder setLocation(Vector3f location) {
        this.location = location;
        return this;
    }

    /**
     * Set the modifier of the particle velocity. 2.0 makes the initial velocity of the particles twice as large.
     *
     * @param velocityModifier the particle velocity modifier
     */
    public ParticleEmitterBuilder setVelocityModifier(float velocityModifier) {
        this.velocityModifier = velocityModifier;
        return this;
    }

    /**
     * Enables or disable 3D particle generation. If 3D generation is enabled, the z-coordinate of particles is
     * modifiable.
     *
     * @param enable3D whether 3D particle generation is enabled
     */
    public ParticleEmitterBuilder setEnable3D(boolean enable3D) {
        this.enable3D = enable3D;
        return this;
    }

    /**
     * Sets the spawning rate of the particle emitter, i.e. the amount of particles generated every call to
     * 'ParticleEmitter.update()'.
     *
     * @param spawningRate the spawning rate of the particle emitter
     */
    public ParticleEmitterBuilder setSpawningRate(float spawningRate) {
        this.spawningRate = spawningRate;
        return this;
    }

    /**
     * Sets the particle life time in amount of calls to 'ParticleEmitter.update()'. For example, if it is set to 300,
     * then the particle will dissapear amount 'ParticleEmitter.update()' has been called 300 times.
     *
     * @param particleLifeTime the life time of the particle
     */
    public ParticleEmitterBuilder setParticleLifeTime(int particleLifeTime) {
        this.particleLifeTime = particleLifeTime;
        return this;
    }

    /**
     * Sets the gravity acceleration applied to all the particles each call to 'ParticleEmitter.update()'.
     *
     * @param gravity the gravity acceleration
     */
    public ParticleEmitterBuilder setGravity(Vector3f gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * Sets the initial velocity which is taken as base value for the initial velocity for all particles. Note that the
     * actual velocity is randomised.
     *
     * @param initialVelocity the base initial velocity
     */
    public ParticleEmitterBuilder setInitialVelocity(Vector3f initialVelocity) {
        this.initialVelocity = initialVelocity;
        return this;
    }

    public ParticleEmitter createParticleEmitter() {
        return new ParticleEmitter(location, spawningRate, particleLifeTime, gravity, enable3D, initialVelocity,
                velocityModifier);
    }
}