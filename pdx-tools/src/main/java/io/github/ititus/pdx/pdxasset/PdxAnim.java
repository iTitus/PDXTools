package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableFloatList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
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
        if (this.samples.translations.size() % this.frameCount != 0 || this.samples.rotations.size() % this.frameCount != 0 || this.samples.scales.size() % this.frameCount != 0) {
            throw new RuntimeException("illegal size for samples");
        }
    }

    public static record AnimBone(
            String name,
            String sampleTypes,
            ImmutableFloatList translation,
            ImmutableFloatList rotation,
            float scale
    ) {

        public static AnimBone of(String name, PdxRawAssetObject o) {
            String sampleTypes = o.getProperty("sa").expectString();

            ImmutableFloatList translation = o.getProperty("t").expectFloatList();
            if (translation.size() != 3) {
                throw new RuntimeException("illegal size for translation");
            }

            ImmutableFloatList rotation = o.getProperty("q").expectFloatList();
            if (rotation.size() != 4) {
                throw new RuntimeException("illegal size for rotation");
            }

            float scale = o.getProperty("s").expectFloat();

            return new AnimBone(name, sampleTypes, translation, rotation, scale);
        }
    }

    public static record Samples(
            ImmutableList<ImmutableFloatList> translations,
            ImmutableList<ImmutableFloatList> rotations,
            ImmutableFloatList scales
    ) {

        public static Samples of(PdxRawAssetObject o) {
            ImmutableList<ImmutableFloatList> translations;
            if (o.hasProperty("t")) {
                ImmutableFloatList ts = o.getProperty("t").expectFloatList();
                if (ts.size() % 3 != 0) {
                    throw new RuntimeException("illegal size for translation samples");
                }

                MutableList<ImmutableFloatList> mutTranslations = Lists.mutable.empty();
                for (int i = 0; i < ts.size(); i += 3) {
                    mutTranslations.add(FloatLists.immutable.of(ts.get(i), ts.get(i + 1), ts.get(i + 2)));
                }

                translations = mutTranslations.toImmutable();
            } else {
                translations = Lists.immutable.empty();
            }

            ImmutableList<ImmutableFloatList> rotations;
            if (o.hasProperty("q")) {
                ImmutableFloatList qs = o.getProperty("q").expectFloatList();
                if (qs.size() % 4 != 0) {
                    throw new RuntimeException("illegal size for rotation samples");
                }

                MutableList<ImmutableFloatList> mutRotations = Lists.mutable.empty();
                for (int i = 0; i < qs.size(); i += 4) {
                    mutRotations.add(FloatLists.immutable.of(qs.get(i), qs.get(i + 1), qs.get(i + 2), qs.get(i + 3)));
                }

                rotations = mutRotations.toImmutable();
            } else {
                rotations = Lists.immutable.empty();
            }

            ImmutableFloatList scales;
            if (o.hasProperty("s")) {
                scales = o.getProperty("s").expectFloatList();
            } else {
                scales = FloatLists.immutable.empty();
            }

            return new Samples(translations, rotations, scales);
        }
    }
}
