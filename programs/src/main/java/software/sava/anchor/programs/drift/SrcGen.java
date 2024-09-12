package software.sava.anchor.programs.drift;

import org.bouncycastle.util.encoders.Hex;
import software.sava.core.accounts.PublicKey;
import systems.comodal.jsoniter.CharBufferFunction;

interface SrcGen {

  CharBufferFunction<PublicKey> DECODE_HEX = (buf, offset, len) -> PublicKey
      .createPubKey(Hex.decode(new String(buf, offset + 2, len - 2)));

  static String pubKeyConstant(PublicKey publicKey) {
    return publicKey == null ? "null" : '"' + publicKey.toBase58() + '"';
  }

  static PublicKey fromBase58Encoded(String base58) {
    return base58 == null ? null : PublicKey.fromBase58Encoded(base58);
  }

  String toSrc();
}
