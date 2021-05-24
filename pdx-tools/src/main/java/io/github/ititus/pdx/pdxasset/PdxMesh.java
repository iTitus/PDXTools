package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableFloatList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.nio.file.Path;

public final class PdxMesh implements IPdxAsset {

    private static final ImmutableIntList VERSION = IntLists.immutable.of(1, 0);

    public final ImmutableIntList version;
    public final ImmutableList<Bone> bones;
    public final ImmutableList<Mesh> meshes;
    public final ImmutableList<Locator> locators;

    public PdxMesh(Path path) {
        PdxRawAssetObject data = new PdxRawAsset(path).getData();
        this.version = data.getProperty("pdxasset").expectIntList();
        if (!VERSION.equals(this.version)) {
            throw new IllegalArgumentException("unknown version " + this.version);
        }

        PdxRawAssetObject object = data.getChildOrEmpty("object");
        MutableList<Bone> bones = Lists.mutable.empty();
        MutableList<Mesh> meshes = Lists.mutable.empty();
        object.getChildren().forEach((k, vs) -> {
            for (PdxRawAssetObject v : vs) {
                for (PdxRawAssetObject b : v.getAllChildren("skeleton")) {
                    bones.add(Bone.of(k, b));
                }

                for (PdxRawAssetObject m : v.getAllChildren("mesh")) {
                    meshes.add(Mesh.of(k, m));
                }
            }
        });
        this.bones = bones.toImmutable();
        this.meshes = meshes.toImmutable();

        PdxRawAssetObject locator = data.getChild("locator");
        MutableList<Locator> locators = Lists.mutable.empty();
        locator.getChildren().forEach((k, vs) -> {
            for (PdxRawAssetObject v : vs) {
                locators.add(Locator.of(k, v));
            }
        });
        this.locators = locators.toImmutable();
    }

    public static record Bone(
            String name,
            PdxRawAssetObject o
    ) {

        // TODO: properties: ix (index), tx (transform), pa (parent)
        public static Bone of(String name, PdxRawAssetObject o) {
            return new Bone(name, o);
        }
    }

    public static record Mesh(
            String name,
            PdxRawAssetObject o
    ) {

        // TODO: properties: p (vertices), n (normals), ta (tangents), u0-u3 (uvs), tri (triangles)
        // TODO: children: aabb (properties: min, max), material (properties: shader, diff, n (normal), spec), skin (properties: ix (joints), w (weights), bones)
        public static Mesh of(String name, PdxRawAssetObject o) {
            return new Mesh(name, o);
        }
    }

    public static record Locator(
            String name,
            ImmutableFloatList position,
            ImmutableFloatList rotation
    ) {

        // TODO: check for pa (parent) or tx (full 4x4 transform matrix for translation, rotation & scale)
        public static Locator of(String name, PdxRawAssetObject o) {
            ImmutableFloatList position = o.getProperty("p").expectFloatList();
            if (position.size() != 3) {
                throw new RuntimeException("illegal size for position");
            }

            ImmutableFloatList rotation = o.getProperty("q").expectFloatList();
            if (rotation.size() != 4) {
                throw new RuntimeException("illegal size for rotation");
            }

            return new Locator(name, position, rotation);
        }
    }
}
