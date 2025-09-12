package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.lang.Boolean;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.accounts.PublicKey.readPubKey;

public sealed interface UpdateArgs extends RustEnum permits
  UpdateArgs.V1,
  UpdateArgs.AsUpdateAuthorityV2,
  UpdateArgs.AsAuthorityItemDelegateV2,
  UpdateArgs.AsCollectionDelegateV2,
  UpdateArgs.AsDataDelegateV2,
  UpdateArgs.AsProgrammableConfigDelegateV2,
  UpdateArgs.AsDataItemDelegateV2,
  UpdateArgs.AsCollectionItemDelegateV2,
  UpdateArgs.AsProgrammableConfigItemDelegateV2 {

  static UpdateArgs read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      case 1 -> AsUpdateAuthorityV2.read(_data, i);
      case 2 -> AsAuthorityItemDelegateV2.read(_data, i);
      case 3 -> AsCollectionDelegateV2.read(_data, i);
      case 4 -> AsDataDelegateV2.read(_data, i);
      case 5 -> AsProgrammableConfigDelegateV2.read(_data, i);
      case 6 -> AsDataItemDelegateV2.read(_data, i);
      case 7 -> AsCollectionItemDelegateV2.read(_data, i);
      case 8 -> AsProgrammableConfigItemDelegateV2.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [UpdateArgs]", ordinal
      ));
    };
  }

  record V1(PublicKey newUpdateAuthority,
            Data data,
            Boolean primarySaleHappened,
            Boolean isMutable,
            CollectionToggle collection,
            CollectionDetailsToggle collectionDetails,
            UsesToggle uses,
            RuleSetToggle ruleSet,
            AuthorizationData authorizationData) implements UpdateArgs {

    public static V1 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var newUpdateAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
      if (newUpdateAuthority != null) {
        i += 32;
      }
      final var data = _data[i++] == 0 ? null : Data.read(_data, i);
      if (data != null) {
        i += Borsh.len(data);
      }
      final var primarySaleHappened = _data[i++] == 0 ? null : _data[i] == 1;
      if (primarySaleHappened != null) {
        ++i;
      }
      final var isMutable = _data[i++] == 0 ? null : _data[i] == 1;
      if (isMutable != null) {
        ++i;
      }
      final var collection = CollectionToggle.read(_data, i);
      i += Borsh.len(collection);
      final var collectionDetails = CollectionDetailsToggle.read(_data, i);
      i += Borsh.len(collectionDetails);
      final var uses = UsesToggle.read(_data, i);
      i += Borsh.len(uses);
      final var ruleSet = RuleSetToggle.read(_data, i);
      i += Borsh.len(ruleSet);
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new V1(newUpdateAuthority,
                    data,
                    primarySaleHappened,
                    isMutable,
                    collection,
                    collectionDetails,
                    uses,
                    ruleSet,
                    authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeOptional(newUpdateAuthority, _data, i);
      i += Borsh.writeOptional(data, _data, i);
      i += Borsh.writeOptional(primarySaleHappened, _data, i);
      i += Borsh.writeOptional(isMutable, _data, i);
      i += Borsh.write(collection, _data, i);
      i += Borsh.write(collectionDetails, _data, i);
      i += Borsh.write(uses, _data, i);
      i += Borsh.write(ruleSet, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + (newUpdateAuthority == null ? 1 : (1 + 32))
           + (data == null ? 1 : (1 + Borsh.len(data)))
           + (primarySaleHappened == null ? 1 : (1 + 1))
           + (isMutable == null ? 1 : (1 + 1))
           + Borsh.len(collection)
           + Borsh.len(collectionDetails)
           + Borsh.len(uses)
           + Borsh.len(ruleSet)
           + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record AsUpdateAuthorityV2(PublicKey newUpdateAuthority,
                             Data data,
                             Boolean primarySaleHappened,
                             Boolean isMutable,
                             CollectionToggle collection,
                             CollectionDetailsToggle collectionDetails,
                             UsesToggle uses,
                             RuleSetToggle ruleSet,
                             TokenStandard tokenStandard,
                             AuthorizationData authorizationData) implements UpdateArgs {

    public static AsUpdateAuthorityV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var newUpdateAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
      if (newUpdateAuthority != null) {
        i += 32;
      }
      final var data = _data[i++] == 0 ? null : Data.read(_data, i);
      if (data != null) {
        i += Borsh.len(data);
      }
      final var primarySaleHappened = _data[i++] == 0 ? null : _data[i] == 1;
      if (primarySaleHappened != null) {
        ++i;
      }
      final var isMutable = _data[i++] == 0 ? null : _data[i] == 1;
      if (isMutable != null) {
        ++i;
      }
      final var collection = CollectionToggle.read(_data, i);
      i += Borsh.len(collection);
      final var collectionDetails = CollectionDetailsToggle.read(_data, i);
      i += Borsh.len(collectionDetails);
      final var uses = UsesToggle.read(_data, i);
      i += Borsh.len(uses);
      final var ruleSet = RuleSetToggle.read(_data, i);
      i += Borsh.len(ruleSet);
      final var tokenStandard = _data[i++] == 0 ? null : TokenStandard.read(_data, i);
      if (tokenStandard != null) {
        i += Borsh.len(tokenStandard);
      }
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsUpdateAuthorityV2(newUpdateAuthority,
                                     data,
                                     primarySaleHappened,
                                     isMutable,
                                     collection,
                                     collectionDetails,
                                     uses,
                                     ruleSet,
                                     tokenStandard,
                                     authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeOptional(newUpdateAuthority, _data, i);
      i += Borsh.writeOptional(data, _data, i);
      i += Borsh.writeOptional(primarySaleHappened, _data, i);
      i += Borsh.writeOptional(isMutable, _data, i);
      i += Borsh.write(collection, _data, i);
      i += Borsh.write(collectionDetails, _data, i);
      i += Borsh.write(uses, _data, i);
      i += Borsh.write(ruleSet, _data, i);
      i += Borsh.writeOptional(tokenStandard, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + (newUpdateAuthority == null ? 1 : (1 + 32))
           + (data == null ? 1 : (1 + Borsh.len(data)))
           + (primarySaleHappened == null ? 1 : (1 + 1))
           + (isMutable == null ? 1 : (1 + 1))
           + Borsh.len(collection)
           + Borsh.len(collectionDetails)
           + Borsh.len(uses)
           + Borsh.len(ruleSet)
           + (tokenStandard == null ? 1 : (1 + Borsh.len(tokenStandard)))
           + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record AsAuthorityItemDelegateV2(PublicKey newUpdateAuthority,
                                   Boolean primarySaleHappened,
                                   Boolean isMutable,
                                   TokenStandard tokenStandard,
                                   AuthorizationData authorizationData) implements UpdateArgs {

    public static AsAuthorityItemDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var newUpdateAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
      if (newUpdateAuthority != null) {
        i += 32;
      }
      final var primarySaleHappened = _data[i++] == 0 ? null : _data[i] == 1;
      if (primarySaleHappened != null) {
        ++i;
      }
      final var isMutable = _data[i++] == 0 ? null : _data[i] == 1;
      if (isMutable != null) {
        ++i;
      }
      final var tokenStandard = _data[i++] == 0 ? null : TokenStandard.read(_data, i);
      if (tokenStandard != null) {
        i += Borsh.len(tokenStandard);
      }
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsAuthorityItemDelegateV2(newUpdateAuthority,
                                           primarySaleHappened,
                                           isMutable,
                                           tokenStandard,
                                           authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeOptional(newUpdateAuthority, _data, i);
      i += Borsh.writeOptional(primarySaleHappened, _data, i);
      i += Borsh.writeOptional(isMutable, _data, i);
      i += Borsh.writeOptional(tokenStandard, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + (newUpdateAuthority == null ? 1 : (1 + 32))
           + (primarySaleHappened == null ? 1 : (1 + 1))
           + (isMutable == null ? 1 : (1 + 1))
           + (tokenStandard == null ? 1 : (1 + Borsh.len(tokenStandard)))
           + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record AsCollectionDelegateV2(CollectionToggle collection, AuthorizationData authorizationData) implements UpdateArgs {

    public static AsCollectionDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var collection = CollectionToggle.read(_data, i);
      i += Borsh.len(collection);
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsCollectionDelegateV2(collection, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(collection, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(collection) + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record AsDataDelegateV2(Data data, AuthorizationData authorizationData) implements UpdateArgs {

    public static AsDataDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var data = _data[i++] == 0 ? null : Data.read(_data, i);
      if (data != null) {
        i += Borsh.len(data);
      }
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsDataDelegateV2(data, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeOptional(data, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + (data == null ? 1 : (1 + Borsh.len(data))) + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record AsProgrammableConfigDelegateV2(RuleSetToggle ruleSet, AuthorizationData authorizationData) implements UpdateArgs {

    public static AsProgrammableConfigDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var ruleSet = RuleSetToggle.read(_data, i);
      i += Borsh.len(ruleSet);
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsProgrammableConfigDelegateV2(ruleSet, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(ruleSet, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(ruleSet) + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 5;
    }
  }

  record AsDataItemDelegateV2(Data data, AuthorizationData authorizationData) implements UpdateArgs {

    public static AsDataItemDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var data = _data[i++] == 0 ? null : Data.read(_data, i);
      if (data != null) {
        i += Borsh.len(data);
      }
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsDataItemDelegateV2(data, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeOptional(data, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + (data == null ? 1 : (1 + Borsh.len(data))) + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 6;
    }
  }

  record AsCollectionItemDelegateV2(CollectionToggle collection, AuthorizationData authorizationData) implements UpdateArgs {

    public static AsCollectionItemDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var collection = CollectionToggle.read(_data, i);
      i += Borsh.len(collection);
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsCollectionItemDelegateV2(collection, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(collection, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(collection) + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 7;
    }
  }

  record AsProgrammableConfigItemDelegateV2(RuleSetToggle ruleSet, AuthorizationData authorizationData) implements UpdateArgs {

    public static AsProgrammableConfigItemDelegateV2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var ruleSet = RuleSetToggle.read(_data, i);
      i += Borsh.len(ruleSet);
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new AsProgrammableConfigItemDelegateV2(ruleSet, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(ruleSet, _data, i);
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(ruleSet) + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 8;
    }
  }
}
