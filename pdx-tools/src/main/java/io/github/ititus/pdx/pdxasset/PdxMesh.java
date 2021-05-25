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

        MutableList<Bone> bones = Lists.mutable.empty();
        MutableList<Mesh> meshes = Lists.mutable.empty();
        data.getChildOrEmpty("object").getChildren().forEach((shapeName, shapeValues) -> {
            for (PdxRawAssetObject v : shapeValues) {
                if (v.hasChild("skeleton")) {
                    v.getChild("skeleton").getChildren().forEach((boneName, boneValues) -> {
                        for (PdxRawAssetObject boneValue : boneValues) {
                            bones.add(Bone.of(shapeName, boneName, boneValue));
                        }
                    });
                }

                for (PdxRawAssetObject m : v.getChildren("mesh")) {
                    meshes.add(Mesh.of(shapeName, m));
                }
            }
        });
        this.bones = bones.toImmutable();
        this.meshes = meshes.toImmutable();

        MutableList<Locator> locators = Lists.mutable.empty();
        data.getChild("locator").getChildren().forEach((k, vs) -> {
            for (PdxRawAssetObject v : vs) {
                locators.add(Locator.of(k, v));
            }
        });
        this.locators = locators.toImmutable();
    }

    public record Bone(
            String shapeName,
            String boneName,
            int index,
            int parent,
            ImmutableFloatList transform
    ) {

        public static Bone of(String shapeName, String boneName, PdxRawAssetObject o) {
            int propertyCount = 2;

            int index = o.getProperty("ix").expectInt();

            int parent;
            if (o.hasProperty("pa")) {
                propertyCount++;

                parent = o.getProperty("pa").expectInt();
            } else {
                parent = -1;
            }

            ImmutableFloatList transform = o.getProperty("tx").expectFloatList();
            if (transform.size() != 12) {
                throw new RuntimeException("illegal size for bone transform");
            }

            if (o.getProperties().size() != propertyCount || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in bone");
            }

            return new Bone(shapeName, boneName, index, parent, transform);
        }
    }

    public record Mesh(
            String name,
            ImmutableList<ImmutableFloatList> vertices,
            ImmutableList<ImmutableFloatList> normals,
            ImmutableList<ImmutableFloatList> tangents,
            ImmutableList<ImmutableFloatList> uv0,
            ImmutableList<ImmutableFloatList> uv1,
            ImmutableList<ImmutableFloatList> uv2,
            ImmutableList<ImmutableFloatList> uv3,
            ImmutableList<ImmutableIntList> triangles,
            Aabb aabb,
            Material material,
            Skin skin
    ) {

        public static Mesh of(String name, PdxRawAssetObject o) {
            int propertyCount = 2;
            int childCount = 2;

            ImmutableList<ImmutableFloatList> vertices = o.getProperty("p").expectGroupedFloatList(3);

            ImmutableList<ImmutableFloatList> normals;
            if (o.hasProperty("n")) {
                propertyCount++;

                normals = o.getProperty("n").expectGroupedFloatList(3);
            } else {
                normals = null;
            }

            ImmutableList<ImmutableFloatList> tangents;
            if (o.hasProperty("ta")) {
                propertyCount++;

                tangents = o.getProperty("ta").expectGroupedFloatList(4);
            } else {
                tangents = null;
            }

            ImmutableList<ImmutableFloatList> uv0;
            if (o.hasProperty("u0")) {
                propertyCount++;

                uv0 = o.getProperty("u0").expectGroupedFloatList(2);
            } else {
                uv0 = null;
            }

            ImmutableList<ImmutableFloatList> uv1;
            if (o.hasProperty("u1")) {
                propertyCount++;

                uv1 = o.getProperty("u1").expectGroupedFloatList(2);
            } else {
                uv1 = null;
            }

            ImmutableList<ImmutableFloatList> uv2;
            if (o.hasProperty("u2")) {
                propertyCount++;

                uv2 = o.getProperty("u2").expectGroupedFloatList(2);
            } else {
                uv2 = null;
            }

            ImmutableList<ImmutableFloatList> uv3;
            if (o.hasProperty("u3")) {
                propertyCount++;

                uv3 = o.getProperty("u3").expectGroupedFloatList(2);
            } else {
                uv3 = null;
            }

            ImmutableList<ImmutableIntList> triangles = o.getProperty("tri").expectGroupedIntList(3);

            Aabb aabb = Aabb.of(o.getChild("aabb"));

            Material material = Material.of(o.getChild("material"));

            Skin skin;
            if (o.hasChild("skin")) {
                childCount++;

                skin = Skin.of(o.getChild("skin"));
            } else {
                skin = null;
            }

            if ((normals != null && normals.size() != vertices.size())
                    || (tangents != null && tangents.size() != vertices.size())
                    || (uv0 != null && uv0.size() != vertices.size())
                    || (uv1 != null && uv1.size() != vertices.size())
                    || (uv2 != null && uv2.size() != vertices.size())
                    || (uv3 != null && uv3.size() != vertices.size())) {
                throw new RuntimeException("illegal size in mesh");
            }

            if (o.getProperties().size() != propertyCount || o.getChildren().size() != childCount) {
                throw new RuntimeException("unexpected properties or children in mesh");
            }

            return new Mesh(name, vertices.toImmutable(), normals, tangents, uv0, uv1, uv2, uv3, triangles, aabb, material, skin);
        }
    }

    public record Locator(
            String name,
            ImmutableFloatList position,
            ImmutableFloatList rotation,
            ImmutableFloatList transform,
            String parent
    ) {

        public static Locator of(String name, PdxRawAssetObject o) {
            int propertyCount = 2;

            ImmutableFloatList position = o.getProperty("p").expectFloatList();
            if (position.size() != 3) {
                throw new RuntimeException("illegal size for locator position");
            }

            ImmutableFloatList rotation = o.getProperty("q").expectFloatList();
            if (rotation.size() != 4) {
                throw new RuntimeException("illegal size for locator rotation");
            }

            ImmutableFloatList transform;
            if (o.hasProperty("tx")) {
                propertyCount++;

                transform = o.getProperty("tx").expectFloatList();
                if (transform.size() != 16) {
                    throw new RuntimeException("illegal size for locator transform");
                }
            } else {
                transform = null;
            }

            String parent;
            if (o.hasProperty("pa")) {
                propertyCount++;

                parent = o.getProperty("pa").expectString();
            } else {
                parent = null;
            }

            if (o.getProperties().size() != propertyCount || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in locator");
            }

            return new Locator(name, position, rotation, transform, parent);
        }
    }

    public record Aabb(
            ImmutableFloatList min,
            ImmutableFloatList max
    ) {

        public static Aabb of(PdxRawAssetObject o) {
            ImmutableFloatList min = o.getProperty("min").expectFloatList();
            if (min.size() != 3) {
                throw new RuntimeException("illegal size for aabb min");
            }

            ImmutableFloatList max = o.getProperty("max").expectFloatList();
            if (max.size() != 3) {
                throw new RuntimeException("illegal size for aabb max");
            }

            if (o.getProperties().size() != 2 || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in aabb");
            }

            return new Aabb(min, max);
        }
    }

    public record Material(
            String shader,
            String diffuse,
            String normal,
            String specular
    ) {

        public static Material of(PdxRawAssetObject o) {
            int propertyCount = 0;

            String shader;
            if (o.hasProperty("shader")) {
                propertyCount++;

                shader = o.getProperty("shader").expectString();
            } else {
                shader = null;
            }

            String diffuse;
            if (o.hasProperty("diff")) {
                propertyCount++;

                diffuse = o.getProperty("diff").expectString();
            } else {
                diffuse = null;
            }

            String normal;
            if (o.hasProperty("n")) {
                propertyCount++;

                normal = o.getProperty("n").expectString();
            } else {
                normal = null;
            }

            String specular;
            if (o.hasProperty("spec")) {
                propertyCount++;

                specular = o.getProperty("spec").expectString();
            } else {
                specular = null;
            }

            if (o.getProperties().size() != propertyCount || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in material");
            }

            return new Material(shader, diffuse, normal, specular);
        }
    }

    public record Skin(
            int bones,
            ImmutableIntList indices,
            ImmutableFloatList weights
    ) {

        public static Skin of(PdxRawAssetObject o) {
            int bones = o.getProperty("bones").expectInt();

            ImmutableIntList indices = o.getProperty("ix").expectIntList();

            ImmutableFloatList weights = o.getProperty("w").expectFloatList();
            if (indices.size() != weights.size()) {
                throw new RuntimeException("illegal size for skin indices and weights");
            }

            if (o.getProperties().size() != 3 || o.getChildren().size() != 0) {
                throw new RuntimeException("unexpected properties or children in skin");
            }

            return new Skin(bones, indices, weights);
        }
    }
}
