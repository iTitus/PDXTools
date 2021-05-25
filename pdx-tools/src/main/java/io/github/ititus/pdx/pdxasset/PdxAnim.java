package io.github.ititus.pdx.pdxasset;

import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.math.vector.Vec4f;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableFloatList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.nio.file.Path;

public final class PdxAnim implements IPdxAsset {

    private static final ImmutableIntList VERSION = IntLists.immutable.of(1, 0);

    public final ImmutableIntList version;
    public final int frameCount;
    public final float fps;
    public final ImmutableList<AnimBone> animBones;
    public final Samples samples;

    public PdxAnim(Path path) {
        PdxRawAssetObject data = new PdxRawAsset(path).getData();
        this.version = data.getProperty("pdxasset").expectIntList();
        if (!VERSION.equals(this.version)) {
            throw new RuntimeException("unknown version " + this.version);
        }

        PdxRawAssetObject info = data.getChild("info");
        this.frameCount = info.getProperty("sa").expectInt();
        this.fps = info.getProperty("fps").expectFloat();

        MutableList<AnimBone> animBones = Lists.mutable.empty();
        info.getChildren().forEach((k, vs) -> {
            for (PdxRawAssetObject v : vs) {
                animBones.add(AnimBone.of(k, v));
            }
        });
        this.animBones = animBones.toImmutable();

        PdxRawAssetObject samples = data.getChild("samples");
        this.samples = Samples.of(samples);
        if ((this.samples.translations != null && this.samples.translations.size() % this.frameCount != 0)
                || (this.samples.rotations != null && this.samples.rotations.size() % this.frameCount != 0)
                || (this.samples.scales != null && this.samples.scales.size() % this.frameCount != 0)) {
            throw new RuntimeException("illegal size for samples");
        }
    }

    public record AnimBone(
            String name,
            String sampleTypes,
            Vec3f translation,
            Vec4f rotation,
            float scale
    ) {

        public static AnimBone of(String name, PdxRawAssetObject o) {
            String sampleTypes = o.getProperty("sa").expectString();

            Vec3f translation = o.getProperty("t").expectVec3f();

            Vec4f rotation = o.getProperty("q").expectVec4f();

            float scale = o.getProperty("s").expectFloat();

            if (o.getProperties().size() != 4 || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in anim bone");
            }

            return new AnimBone(name, sampleTypes, translation, rotation, scale);
        }
    }

    public record Samples(
            ImmutableList<Vec3f> translations,
            ImmutableList<Vec4f> rotations,
            ImmutableFloatList scales
    ) {

        public static Samples of(PdxRawAssetObject o) {
            int propertyCount = 0;

            ImmutableList<Vec3f> translations;
            if (o.hasProperty("t")) {
                propertyCount++;

                translations = o.getProperty("t").expectVec3fList();
            } else {
                translations = null;
            }

            ImmutableList<Vec4f> rotations;
            if (o.hasProperty("q")) {
                propertyCount++;

                rotations = o.getProperty("q").expectVec4fList();
            } else {
                rotations = null;
            }

            ImmutableFloatList scales;
            if (o.hasProperty("s")) {
                propertyCount++;

                scales = o.getProperty("s").expectFloatList();
            } else {
                scales = null;
            }

            if (o.getProperties().size() != propertyCount || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in anim samples");
            }

            return new Samples(translations, rotations, scales);
        }
    }
}
