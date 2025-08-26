package software.sava.anchor.programs.glam.mint.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record MintModel(String symbol, byte[] _symbol,
                        byte[] name,
                        String uri, byte[] _uri,
                        PublicKey asset,
                        String imageUri, byte[] _imageUri,
                        OptionalInt yearInSeconds,
                        PublicKey permanentDelegate,
                        Boolean defaultAccountStateFrozen,
                        FeeStructure feeStructure,
                        NotifyAndSettle notifyAndSettle,
                        OptionalInt lockupPeriod,
                        OptionalLong maxCap,
                        OptionalLong minSubscription,
                        OptionalLong minRedemption,
                        Boolean subscriptionPaused,
                        Boolean redemptionPaused,
                        PublicKey[] allowlist,
                        PublicKey[] blocklist) implements Borsh {

  public static MintModel createRecord(final String symbol,
                                       final byte[] name,
                                       final String uri,
                                       final PublicKey asset,
                                       final String imageUri,
                                       final OptionalInt yearInSeconds,
                                       final PublicKey permanentDelegate,
                                       final Boolean defaultAccountStateFrozen,
                                       final FeeStructure feeStructure,
                                       final NotifyAndSettle notifyAndSettle,
                                       final OptionalInt lockupPeriod,
                                       final OptionalLong maxCap,
                                       final OptionalLong minSubscription,
                                       final OptionalLong minRedemption,
                                       final Boolean subscriptionPaused,
                                       final Boolean redemptionPaused,
                                       final PublicKey[] allowlist,
                                       final PublicKey[] blocklist) {
    return new MintModel(symbol, Borsh.getBytes(symbol),
                         name,
                         uri, Borsh.getBytes(uri),
                         asset,
                         imageUri, Borsh.getBytes(imageUri),
                         yearInSeconds,
                         permanentDelegate,
                         defaultAccountStateFrozen,
                         feeStructure,
                         notifyAndSettle,
                         lockupPeriod,
                         maxCap,
                         minSubscription,
                         minRedemption,
                         subscriptionPaused,
                         redemptionPaused,
                         allowlist,
                         blocklist);
  }

  public static MintModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var symbol = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (symbol != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var name = _data[i++] == 0 ? null : new byte[32];
    if (name != null) {
      i += Borsh.readArray(name, _data, i);
    }
    final var uri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (uri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var asset = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (asset != null) {
      i += 32;
    }
    final var imageUri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (imageUri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var yearInSeconds = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (yearInSeconds.isPresent()) {
      i += 4;
    }
    final var permanentDelegate = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (permanentDelegate != null) {
      i += 32;
    }
    final var defaultAccountStateFrozen = _data[i++] == 0 ? null : _data[i] == 1;
    if (defaultAccountStateFrozen != null) {
      ++i;
    }
    final var feeStructure = _data[i++] == 0 ? null : FeeStructure.read(_data, i);
    if (feeStructure != null) {
      i += Borsh.len(feeStructure);
    }
    final var notifyAndSettle = _data[i++] == 0 ? null : NotifyAndSettle.read(_data, i);
    if (notifyAndSettle != null) {
      i += Borsh.len(notifyAndSettle);
    }
    final var lockupPeriod = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (lockupPeriod.isPresent()) {
      i += 4;
    }
    final var maxCap = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxCap.isPresent()) {
      i += 8;
    }
    final var minSubscription = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minSubscription.isPresent()) {
      i += 8;
    }
    final var minRedemption = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minRedemption.isPresent()) {
      i += 8;
    }
    final var subscriptionPaused = _data[i++] == 0 ? null : _data[i] == 1;
    if (subscriptionPaused != null) {
      ++i;
    }
    final var redemptionPaused = _data[i++] == 0 ? null : _data[i] == 1;
    if (redemptionPaused != null) {
      ++i;
    }
    final var allowlist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (allowlist != null) {
      i += Borsh.lenVector(allowlist);
    }
    final var blocklist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    return new MintModel(symbol, Borsh.getBytes(symbol),
                         name,
                         uri, Borsh.getBytes(uri),
                         asset,
                         imageUri, Borsh.getBytes(imageUri),
                         yearInSeconds,
                         permanentDelegate,
                         defaultAccountStateFrozen,
                         feeStructure,
                         notifyAndSettle,
                         lockupPeriod,
                         maxCap,
                         minSubscription,
                         minRedemption,
                         subscriptionPaused,
                         redemptionPaused,
                         allowlist,
                         blocklist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalVector(_symbol, _data, i);
    if (name == null || name.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArray(name, _data, i);
    }
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptional(asset, _data, i);
    i += Borsh.writeOptionalVector(_imageUri, _data, i);
    i += Borsh.writeOptional(yearInSeconds, _data, i);
    i += Borsh.writeOptional(permanentDelegate, _data, i);
    i += Borsh.writeOptional(defaultAccountStateFrozen, _data, i);
    i += Borsh.writeOptional(feeStructure, _data, i);
    i += Borsh.writeOptional(notifyAndSettle, _data, i);
    i += Borsh.writeOptional(lockupPeriod, _data, i);
    i += Borsh.writeOptional(maxCap, _data, i);
    i += Borsh.writeOptional(minSubscription, _data, i);
    i += Borsh.writeOptional(minRedemption, _data, i);
    i += Borsh.writeOptional(subscriptionPaused, _data, i);
    i += Borsh.writeOptional(redemptionPaused, _data, i);
    if (allowlist == null || allowlist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(allowlist, _data, i);
    }
    if (blocklist == null || blocklist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(blocklist, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return (_symbol == null || _symbol.length == 0 ? 1 : (1 + Borsh.lenVector(_symbol)))
         + (name == null || name.length == 0 ? 1 : (1 + Borsh.lenArray(name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (asset == null ? 1 : (1 + 32))
         + (_imageUri == null || _imageUri.length == 0 ? 1 : (1 + Borsh.lenVector(_imageUri)))
         + (yearInSeconds == null || yearInSeconds.isEmpty() ? 1 : (1 + 4))
         + (permanentDelegate == null ? 1 : (1 + 32))
         + (defaultAccountStateFrozen == null ? 1 : (1 + 1))
         + (feeStructure == null ? 1 : (1 + Borsh.len(feeStructure)))
         + (notifyAndSettle == null ? 1 : (1 + Borsh.len(notifyAndSettle)))
         + (lockupPeriod == null || lockupPeriod.isEmpty() ? 1 : (1 + 4))
         + (maxCap == null || maxCap.isEmpty() ? 1 : (1 + 8))
         + (minSubscription == null || minSubscription.isEmpty() ? 1 : (1 + 8))
         + (minRedemption == null || minRedemption.isEmpty() ? 1 : (1 + 8))
         + (subscriptionPaused == null ? 1 : (1 + 1))
         + (redemptionPaused == null ? 1 : (1 + 1))
         + (allowlist == null || allowlist.length == 0 ? 1 : (1 + Borsh.lenVector(allowlist)))
         + (blocklist == null || blocklist.length == 0 ? 1 : (1 + Borsh.lenVector(blocklist)));
  }
}
