package io.github.ititus.pdx.pdxasset;

import io.github.ititus.commons.math.vector.Vec2f;
import io.github.ititus.commons.math.vector.Vec3f;
import io.github.ititus.commons.math.vector.Vec3i;
import io.github.ititus.commons.math.vector.Vec4f;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableFloatList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.nio.file.Path;

public final class PdxMesh implements IPdxAsset {

    private static final ImmutableIntList VERSION = IntLists.immutable.of(1, 0);

    public final Path path;
    public final ImmutableIntList version;
    public final ImmutableList<Bone> bones;
    public final ImmutableList<Mesh> meshes;
    public final ImmutableList<Locator> locators;

    public PdxMesh(Path path) {
        this.path = path;

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
            ImmutableList<Vec3f> vertices,
            ImmutableList<Vec3f> normals,
            ImmutableList<Vec4f> tangents,
            ImmutableList<Vec2f> uv0,
            ImmutableList<Vec2f> uv1,
            ImmutableList<Vec2f> uv2,
            ImmutableList<Vec2f> uv3,
            ImmutableList<Vec3i> triangles,
            Aabb aabb,
            Material material,
            Skin skin
    ) {

        public static Mesh of(String name, PdxRawAssetObject o) {
            int propertyCount = 2;
            int childCount = 2;

            ImmutableList<Vec3f> vertices = o.getProperty("p").expectVec3fList();

            ImmutableList<Vec3f> normals;
            if (o.hasProperty("n")) {
                propertyCount++;

                normals = o.getProperty("n").expectVec3fList();
            } else {
                normals = null;
            }

            ImmutableList<Vec4f> tangents;
            if (o.hasProperty("ta")) {
                propertyCount++;

                tangents = o.getProperty("ta").expectVec4fList();
            } else {
                tangents = null;
            }

            ImmutableList<Vec2f> uv0;
            if (o.hasProperty("u0")) {
                propertyCount++;

                uv0 = o.getProperty("u0").expectVec2fList();
            } else {
                uv0 = null;
            }

            ImmutableList<Vec2f> uv1;
            if (o.hasProperty("u1")) {
                propertyCount++;

                uv1 = o.getProperty("u1").expectVec2fList();
            } else {
                uv1 = null;
            }

            ImmutableList<Vec2f> uv2;
            if (o.hasProperty("u2")) {
                propertyCount++;

                uv2 = o.getProperty("u2").expectVec2fList();
            } else {
                uv2 = null;
            }

            ImmutableList<Vec2f> uv3;
            if (o.hasProperty("u3")) {
                propertyCount++;

                uv3 = o.getProperty("u3").expectVec2fList();
            } else {
                uv3 = null;
            }

            ImmutableList<Vec3i> triangles = o.getProperty("tri").expectVec3iList();

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
            Vec3f position,
            Vec4f rotation,
            ImmutableFloatList transform,
            String parent
    ) {

        public static Locator of(String name, PdxRawAssetObject o) {
            int propertyCount = 2;

            Vec3f position = o.getProperty("p").expectVec3f();

            Vec4f rotation = o.getProperty("q").expectVec4f();

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
            Vec3f min,
            Vec3f max
    ) {

        public static Aabb of(PdxRawAssetObject o) {
            Vec3f min = o.getProperty("min").expectVec3f();
            Vec3f max = o.getProperty("max").expectVec3f();

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
